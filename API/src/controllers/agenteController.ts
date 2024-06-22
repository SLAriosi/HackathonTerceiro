import { Request, Response } from "express";
import knex from "../database/knex";
import AppError from "../utils/AppError";

type Agentes = {
        id: number;
        nome: string;
        username: string;
        password: string;
};

export const getAgente = async (req: Request, res: Response) => {
    const agentes: Agentes[] = await knex("agente-saude");

    if (agentes.length === 0) {
        throw new AppError("Nenhum agente encontrado!");
    }
    res.json(agentes);
};