import { Request, Response } from "express";
import knex from "../database/knex";
import AppError from "../utils/AppError";

type Vacinas = {
    nome: string;
    data_campanha_inicio: Date;
    data_campanha_fim: Date;
};


export const getVacina = async (req: Request, res: Response) => {
    const vacina: Vacinas = await knex("vacina");

    if (!vacina) {
        throw new AppError("Nenhuma vacina cadastrada!");
    }
    res.json(vacina);
};

export const postVacina = async (req: Request, res: Response) => {
    const obj: Vacinas = req.body;

    const id_vacina = await knex("vacina").insert(obj);

    res.json({
        vacina: obj,
    });
};

export const putVacina = async (req: Request, res: Response) => {
    const obj: Vacinas = req.body;
    const { id } = req.params;

    let vacina = await knex("vacina").where({ id }).first();

    if (!vacina?.id) {
        throw new AppError("Usuario não encontrado");
    }

    vacina = {
        ...vacina,
        ...obj,
    };

    await knex("vacina").update(vacina).where({ id: vacina.id });

    return res.json({
        message: "Editado usuario com sucesso!",
        vacina: vacina,
    });
};

export const deleteVacina = async (req: Request, res: Response) => {
    const { id } = req.params;

    let vacina = await knex("vacina").where({ id }).first();

    if (!vacina?.id) {
        throw new AppError("Vacina não encontrada");
    }

    await knex("vacina").where({ id }).delete();

    return res.json({
        message: "Vacina deletada com sucesso!",
    });
};
