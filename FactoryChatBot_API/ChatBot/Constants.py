import os
from pathlib import Path

from dotenv import load_dotenv
from chromadb.config import Settings
from langchain_core.prompts import PromptTemplate
import chromadb
from langchain_huggingface import HuggingFaceEmbeddings

load_dotenv()
PROJECT_ROOT = Path(__file__).resolve().parent.parent

persist_directory =  os.path.join(PROJECT_ROOT, os.environ.get('PERSIST_DIRECTORY'))


source_directory = os.environ.get('SOURCE_DIRECTORY', 'source_documents')
embeddings_id = os.environ.get('EMBEDDINGS_ID')

chunk_size = 1000
chunk_overlap = 100
embeddings = HuggingFaceEmbeddings(model_name=embeddings_id)
MODEL = os.getenv('MODEL_ID')


if persist_directory is None:
    raise Exception("Please set the PERSIST_DIRECTORY environment variable")

CHROMA_SETTINGS = Settings(
    anonymized_telemetry=False
)

prompt = PromptTemplate(
    template="""You are an assistant for question-answering tasks.
    Use the following documents to answer the question.
    If you don't know the answer, just say that you don't know.
    keep the answer concise:
    Question: {question}
    Documents: {documents}
    Answer:
    """,
    input_variables=["question", "documents"],
)
sql_prompt = PromptTemplate(
    template="""You are a MySQL expert. Given an input question, create a syntactically correct MySQL query to run.

    CRITICAL INSTRUCTIONS:
    - Output ONLY the SQL query, nothing else
    - Do NOT include any explanations, comments, or markdown
    - Do NOT wrap the query in ```sql``` code blocks
    - Do NOT add any text before or after the query
    - Query for at most {top_k} results using LIMIT clause unless user specifies otherwise
    - Only use columns that exist in the schema
    - Only query the columns needed to answer the question

    Database Schema from YAML:
    {yaml_context}

    Available Tables:
    {table_info}

    Question: {input}

    Remember: Output ONLY the raw SQL query with no additional text.
    """,
    input_variables=["input", "table_info", "yaml_context", "top_k"],
)
sql_answer_prompt = PromptTemplate(
    template="""Given the following user question, corresponding SQL query, and SQL result, answer the user question.

    Question: {question}
    SQL Query: {query}
    SQL Result: {result}
    Answer:
    """,
    input_variables=["question", "query", "result"],
)

# sql_prompt = PromptTemplate(
#     template="""You are an expert SQL assistant. Your task is to generate a valid SQL query based on the user's question and the database schema provided. Use only the information in the schema context below. Ensure your query is syntactically correct, semantically accurate, and limited to SELECT queries only.
#     Guidelines:
#     - Only use tables, columns, and relationships defined in the schema.
#     - Do not invent column or table names. If something is unclear, write a SQL comment.
#     - Use JOINs where necessary to combine data across tables.
#     - Use GROUP BY and aggregate functions when the question implies summarization.
#     - Always use LIMIT in queries requesting a preview or top-N results.
#     - Format queries cleanly with appropriate indentation.
#     - Never write DELETE, INSERT, UPDATE, DROP, or DDL statements.
#     - Prefer using table aliases (`c` for customers, `o` for orders) when dealing with multiple tables.
#     - All dates must follow 'YYYY-MM-DD'  or 'YYYY-MM-DD HH-mm-ss format.
#
#     Database Schema from YAML:
#     {yaml_context}
#
#     Available Tables:
#     {table_info}
#
#     Question: {input}
#     SQL Query:
#     """,
#     input_variables=["input", "table_info", "yaml_context"],
# )
#
# sql_answer_prompt = PromptTemplate(
#     template="""Given the following user question, corresponding SQL query, and SQL result, answer the user question.
#
#     Question: {question}
#     SQL Query: {query}
#     SQL Result: {result}
#     Answer:
#     """,
#     input_variables=["question", "query", "result"],
# )



chroma_client = chromadb.PersistentClient(path=persist_directory, settings=CHROMA_SETTINGS)
