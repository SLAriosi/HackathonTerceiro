import { Request, Response } from "express";
import knex from "../database/knex";
import AppError from "../utils/AppError";

type Agendamentos = {
    data: Date;
    horario: string;
    vacina: string;
    nome: string;
    cpf: string;
    cep: string;
    data_nascimento: Date;
    telefone: string;
};

export const getAgendamento = async (req: Request, res: Response) => {
    const agendamento: Agendamentos = await knex("agendamento_vacina");

    if (!agendamento) {
        throw new AppError("Nenhum agendamento feito!");
    }
    res.json(agendamento);
};

export const postAgendamento = async (req: Request, res: Response) => {
    const obj: Agendamentos = req.body;

    const id_agendamento = await knex("agendamento_vacina").insert(obj);

    res.json({
        agendamento: obj,
    });
};

export const putAgendamento = async (req: Request, res: Response) => {
    const obj: Agendamentos = req.body;
    const { id } = req.params;

    let agendamento = await knex("agendamento_vacina").where({ id }).first();

    if (!agendamento?.id) {
        throw new AppError("Usuario não encontrado");
    }

    agendamento = {
        ...agendamento,
        ...obj,
    };

    await knex("agendamento_vacina").update(agendamento).where({ id: agendamento.id });

    return res.json({
        message: "Editado usuario com sucesso!",
        agendamento: agendamento,
    });
};

export const deleteAgendamento = async (req: Request, res: Response) => {
    const { id } = req.params;

    let agendamento = await knex("agendamento").where({ id }).first();

    if (!agendamento?.id) {
        throw new AppError("Agendamento não encontrado");
    }

    await knex("agendamento").where({ id }).delete();

    return res.json({
        message: "Agendamento deletado com sucesso!",
    });
};
