import { Request, Response } from "express";
import knex from "../database/knex";
import AppError from "../utils/AppError";

type Agente = {
    nome: string;
    username:string;
    password: string;
};


export const getAgente = async (req: Request, res: Response) => {
    const agente: Agente = await knex("agente-saude");

    if (!agente) {
        throw new AppError("Nenhum agente cadastrada!");
    }
    res.json(agente);
};