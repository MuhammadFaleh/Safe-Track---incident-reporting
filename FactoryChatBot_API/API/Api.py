from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
import ChatBot.Model as Model
from pydantic import BaseModel

llm_rag = Model.RAGApplication(Model.pdf_retriever, Model.chain)
llm_sql = Model.SQLQueryApplication(Model.db, Model.sql_answer_chain, Model.yaml_retriever if Model.yaml_exists else None)

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"]
)


class QueryRequest(BaseModel):
    question: str


class SQLResponse(BaseModel):
    answer: str
    sql_query: str
    sql_result: str


@app.post("/ask-rag")
async def query_rag(request: QueryRequest):
    try:
        answer = llm_rag.run(request.question)
        return {"answer": answer}
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))


@app.post("/ask-sql", response_model=SQLResponse)
async def query_sql(request: QueryRequest):
    if not llm_sql:
        raise HTTPException(status_code=503, detail="SQL database not connected")
    try:
        answer, sql_query, sql_result = llm_sql.run(request.question)
        return {
            "answer": answer,
            "sql_query": sql_query,
            "sql_result": sql_result
        }
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))


@app.get("/health")
async def health_check():
    return {
        "status": "healthy",
        "pdf_db_loaded": Model.pdf_exists,
        "yaml_db_loaded": Model.yaml_exists,
        "sql_db_connected": Model.mysql_url is not None
    }