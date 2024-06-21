import { Request, Response } from "express";
import knex from "../database/knex";
import AppError from "../utils/AppError";
import { z } from "zod";

type Vacinas = {
    nome: string;
    data_campanha_inicio: Date;
    data_campanha_fim: Date;
};

const dateString = z.string().regex(/^\d{4}-\d{2}-\d{2}$/, { message: "Data inválida" }).transform((val) => new Date(val));

export const getVacina = async (req: Request, res: Response) => {
    const vacinas: Vacinas[] = await knex("vacina");

    if (vacinas.length === 0) {
        throw new AppError("Nenhuma vacina cadastrada!");
    }
    res.json(vacinas);
};

export const postVacina = async (req: Request, res: Response) => {
    const bodySchema = z.object({
        nome: z.string({ required_error: "Nome é obrigatório" }),
        data_campanha_inicio: dateString,
        data_campanha_fim: dateString,
    });

    try {
        const obj = bodySchema.parse(req.body);
        const [id_vacina] = await knex("vacina").insert(obj);

        res.json({
            id: id_vacina,
            vacina: obj,
        });
    } catch (error) {
        if (error instanceof z.ZodError) {
            const errorMessages = error.errors.map(e => `${e.path.join('.')} - ${e.message}`).join(', ');
            throw new AppError(`Algum erro de escrita: ${errorMessages}`, 400);
        }
        throw new AppError("Não foi possível criar essa Vacina, verifique as informações e tente novamente", 400);
    }
};

export const putVacina = async (req: Request, res: Response) => {
    const bodySchema = z.object({
        nome: z.string({ required_error: "Nome é obrigatório" }),
        data_campanha_inicio: dateString,
        data_campanha_fim: dateString,
    });

    try {
        const { id } = req.params;
        const obj = bodySchema.parse(req.body);

        let vacina = await knex("vacina").where({ id }).first();

        if (!vacina) {
            throw new AppError("Vacina não encontrada", 404);
        }

        await knex("vacina").update(obj).where({ id });

        return res.json({
            message: "Editado Vacina com sucesso!",
            vacina: { ...vacina, ...obj },
        });
    } catch (error) {
        if (error instanceof z.ZodError) {
            const errorMessages = error.errors.map(e => `${e.path.join('.')} - ${e.message}`).join(', ');
            throw new AppError(`Algum erro de escrita: ${errorMessages}`, 400);
        }
        throw new AppError("Não existe essa Vacina para ser atualizada", 400);
    }
};

export const deleteVacina = async (req: Request, res: Response) => {
    try {
        const { id } = req.params;

        let vacina = await knex("vacina").where({ id }).first();

        if (!vacina) {
            throw new AppError("Vacina não encontrada", 404);
        }

        await knex("vacina").where({ id }).delete();

        return res.json({
            message: "Vacina deletada com sucesso!",
        });
    } catch (error) {
        throw new AppError("Não existe uma Vacina para ser deletada", 400);
    }
};
