import os

import chromadb
import torch
from langchain_classic.chains.sql_database.query import create_sql_query_chain
from langchain_community.tools import QuerySQLDataBaseTool
from langchain_community.utilities import SQLDatabase

import ChatBot.Ingest as Ingest
from langchain_chroma import Chroma
import re
import ChatBot.Constants as Constants
from langchain_ollama import OllamaLLM
from langchain_core.output_parsers import StrOutputParser
from transformers import AutoTokenizer

from torch import cuda, bfloat16


device = f'cuda:{cuda.current_device()}' if cuda.is_available() else 'cpu'

pdf_exists, pdf_db = Ingest.get_vectorstore(db_type='pdf')
yaml_exists, yaml_db = Ingest.get_vectorstore(db_type='yaml')

if pdf_exists and yaml_exists:
    pdf_retriever = pdf_db.as_retriever(k=4)
    yaml_retriever = yaml_db.as_retriever(k=4)

hf_auth = os.getenv('HUGGINGFACEHUB_API_TOKEN')
mysql_url = os.getenv('SQL_db')
db = SQLDatabase.from_uri(mysql_url)

tokenizer = AutoTokenizer.from_pretrained('meta-llama/Llama-3.2-1B-Instruct',token=hf_auth)

llm = OllamaLLM(
    model=Constants.MODEL,
    num_gpu=1,
    temperature=0,
)


chain = Constants.prompt | llm | StrOutputParser()
sql_chain = create_sql_query_chain(llm, db, prompt=Constants.sql_prompt)
sql_answer_chain = Constants.sql_answer_prompt | llm | StrOutputParser()


class RAGApplication:
    def __init__(self, retriever, rag_chain):
        self.retriever = retriever
        self.rag_chain = rag_chain

    def run(self, question):
        documents = self.retriever.invoke(question)
        doc_texts = "\\n".join([doc.page_content for doc in documents])
        answer = self.rag_chain.invoke({"question": question, "documents": doc_texts})
        return answer


class SQLQueryApplication:
    def __init__(self, db, answer_chain, yaml_retriever=None):
        self.db = db
        self.answer_chain = answer_chain
        self.execute_query = QuerySQLDataBaseTool(db=db)
        self.yaml_retriever = yaml_retriever
        self.sql_prompt_chain = Constants.sql_prompt | llm | StrOutputParser()

    def run(self, question):
        yaml_context = ""
        if self.yaml_retriever:
            yaml_docs = self.yaml_retriever.invoke(question)
            yaml_context = "\\n".join([doc.page_content for doc in yaml_docs])

        table_info = self.db.get_table_info()

        sql_query = self.sql_prompt_chain.invoke({
            "input": question,
            "table_info": table_info,
            "yaml_context": yaml_context,
            "top_k": 5
        })
        sql_query = sql_query.strip()
        if sql_query.startswith("```sql"):
            sql_query = sql_query[6:]
        if sql_query.endswith("```"):
            sql_query = sql_query[:-3]
        sql_query = sql_query.strip()

        result = self.execute_query.invoke(sql_query)
        answer = self.answer_chain.invoke({
            "question": question,
            "query": sql_query,
            "result": result
        })
        return answer, sql_query, result

# rag_application = RAGApplication(retriever, chain) ## move to the api
# query="we had a fire what to do tell me the steps"
# answer = rag_application.run(query)
# print("Question:", query)
# print("Answer:", answer)
# query="in case of electrcal fire what to do"
# answer = rag_application.run(query)
# print("Question:", query)
# print("Answer:", answer)
# query="tell me what is the Chemical Spill Response"
# answer = rag_application.run(query)
# print("Question:", query)
# print("Answer:", answer)
# query="what are  the Fire Response Actions"
# answer = rag_application.run(query)
# print("Question:", query)
# print("Answer:", answer)
