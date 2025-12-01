import argparse
import os

import ChatBot.Constants as Constants
from dotenv import load_dotenv
import fitz  # PyMuPDF
from langchain_core.documents import Document
import yaml

import torch
from langchain_text_splitters import RecursiveCharacterTextSplitter
from langchain_chroma import Chroma
from langchain_huggingface.embeddings import HuggingFaceEmbeddings

if not load_dotenv():
    print("please check that env file exist and readable")
    exit(0)

from ChatBot.Constants import CHROMA_SETTINGS
import chromadb

# environment variables

load_dotenv()
persist_directory = Constants.persist_directory
persist_directory_pdf = os.path.join(persist_directory, "pdf_db")
persist_directory_yaml = os.path.join(persist_directory, "yaml_db")

source_directory = Constants.source_directory
embeddings_id = Constants.embeddings_id
embeddings = Constants.embeddings
MODEL = Constants.MODEL
yaml_file_paths=os.getenv("yaml_PATH")
pdf_file_paths=os.getenv("pdf_PATH")


def load_pdf_mupdf(file_path):
    doc = fitz.open(file_path)
    documents = []

    for page_num in range(len(doc)):
        page = doc.load_page(page_num)
        text = page.get_text()
        if text.strip():
            metadata = {"source": file_path, "page": page_num}
            documents.append(Document(page_content=text, metadata=metadata))

    doc.close()
    return documents


def load_yaml(file_path):
    documents = []
    with open(file_path, 'r', encoding='utf-8') as f:
        try:
            yaml_content = yaml.safe_load(f)
            text = yaml.dump(yaml_content, default_flow_style=False, sort_keys=False)
            metadata = {"source": file_path, "filename": os.path.basename(file_path)}
            documents.append(Document(page_content=text, metadata=metadata))
        except yaml.YAMLError as e:
            print(f"error parsing yaml file {file_path}: {e}")
    return documents


def split_documents(documents):
    splitter = RecursiveCharacterTextSplitter(chunk_size=Constants.chunk_size, chunk_overlap=Constants.chunk_overlap)
    return splitter.split_documents(documents)


def create_or_get_chroma_client(persist_dir):
    os.makedirs(persist_dir, exist_ok=True)
    client = chromadb.PersistentClient(path=persist_dir, settings=CHROMA_SETTINGS)
    try:
        collection = client.get_collection("documents")
    except Exception:
        collection = client.create_collection("documents")
    return client, collection


def does_vectorstore_exist(persist_dir, embeddings, chroma_client):
    try:
        db = Chroma(persist_directory=persist_dir, embedding_function=embeddings, client_settings=CHROMA_SETTINGS,
                    client=chroma_client)
    except Exception:
        return False
    if not db.get()['documents']:
        return False
    return True


def ingest_files_to_chroma(file_paths, file_type='pdf'):
    all_documents = []

    if file_type == 'pdf':
        loader = load_pdf_mupdf
        persist_dir = persist_directory_pdf
    else:
        loader = load_yaml
        persist_dir = persist_directory_yaml

    for path in file_paths:
        docs = loader(path)
        all_documents.extend(docs)

    # split all docs into chunks
    chunks = split_documents(all_documents)

    chroma_client, _ = create_or_get_chroma_client(persist_dir)

    # create or load Chroma vectorstore
    if does_vectorstore_exist(persist_dir, embeddings, chroma_client):
        print(f"Appending to existing vectorstore at {persist_dir}")
        db = Chroma(persist_directory=persist_dir, embedding_function=embeddings, client_settings=CHROMA_SETTINGS,
                    client=chroma_client)
        db.add_documents(chunks)
    else:
        print(f"Creating new vectorstore at {persist_dir} (this may take some time)...")
        db = Chroma.from_documents(documents=chunks, embedding=embeddings, persist_directory=persist_dir,
                                   client_settings=CHROMA_SETTINGS, client=chroma_client)

    print(f"Ingestion complete. Total chunks: {len(chunks)}")
    return db


def get_vectorstore(db_type='pdf'):
    if db_type == 'pdf':
        persist_dir = persist_directory_pdf
    else:
        persist_dir = persist_directory_yaml

    chroma_client = chromadb.PersistentClient(settings=CHROMA_SETTINGS, path=persist_dir)
    if does_vectorstore_exist(persist_dir, embeddings, chroma_client):
        return True, Chroma(persist_directory=persist_dir, embedding_function=embeddings,
                            client_settings=CHROMA_SETTINGS,
                            client=chroma_client)
    return False, None


# ingest_files_to_chroma([pdf_file_paths], file_type='pdf')
# exists, db = get_vectorstore(db_type='pdf')
#
# ingest_files_to_chroma([yaml_file_paths], file_type='yaml')
# exists2, db2 = get_vectorstore(db_type='yaml')