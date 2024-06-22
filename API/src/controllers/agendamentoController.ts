import { Request, Response } from "express";
import knex from "../database/knex";
import AppError from "../utils/AppError";
import { z } from "zod";

type Agendamentos = {
    data: Date,
    horario: string,
    vacina: string,
    nome: string,
    cpf: string,
    cep: string,
    telefone: string,
    "agente-saude_id": number,
};

const dateString = z.string().regex(/^\d{4}-\d{2}-\d{2}$/, { message: "Data inválida" }).transform((val) => new Date(val));

export const getAgendamento = async (req: Request, res: Response) => {
    const agendamentos: Agendamentos[] = await knex("agenda");

    if (agendamentos.length === 0) {
        throw new AppError("Nenhum agendamento encontrado!");
    }
    res.json(agendamentos);
};

export const postAgendamento = async (req: Request, res: Response) => {
    const bodySchema = z.object({
        data: dateString,
        horario: z.string().min(5, { message: "Horário é obrigatório" }).max(5, { message: "Digite um horário válido" }), // Assumindo formato HH:MM
        vacina: z.string({ required_error: "Vacina é obrigatória" }),
        nome: z.string({ required_error: "Nome é obrigatório" }),
        cpf: z.string().min(11, { message: "CPF deve ter pelo menos 11 dígitos" }).nonempty({ message: "CPF é obrigatório" }).max(11, { message: "CPF deve ter no máximo 11 dígitos" }),
        cep: z.string().min(8, { message: "CEP deve ter pelo menos 8 dígitos" }).regex(/^\d+$/, { message: "CEP deve conter apenas números" }).max(8, { message: "CEP deve ter no máximo 8 dígitos" }),
        telefone: z.string().min(11, { message: "Telefone deve ter pelo menos 11 caracteres" }).regex(/^\d+$/, { message: "Telefone deve conter apenas números" }).max(11, { message: "Telefone deve ter no máximo 11 dígitos" }),
        "agente-saude_id": z.number({ required_error: "ID do agente de saúde é obrigatório" }),
    });

    try {
        const obj = bodySchema.parse(req.body);
        await knex('agenda').insert(obj);
        res.json({
            agendamento: obj,
        });
    } catch (error) {
        if (error instanceof z.ZodError) {
            const errorMessages = error.errors.map(e => `${e.path.join('.')} - ${e.message}`).join(', ');
            throw new AppError(`Algum erro de escrita: ${errorMessages}`, 400);
        }
        throw new AppError("Não foi possível criar esse Agendamento, verifique as informações e tente novamente", 400);
    }
};

export const putAgendamento = async (req: Request, res: Response) => {
    const bodySchema = z.object({
        data: dateString,
        horario: z.string().min(5, { message: "Horário é obrigatório" }).max(5, { message: "Digite um horário válido" }),
        vacina: z.string({ required_error: "Vacina é obrigatória" }),
        nome: z.string({ required_error: "Nome é obrigatório" }),
        cpf: z.string().min(11, { message: "CPF deve ter pelo menos 11 dígitos" }).max(11, { message: "CPF deve ter no máximo 11 dígitos" }),
        cep: z.string().min(8, { message: "CEP deve ter pelo menos 8 dígitos" }).max(8, { message: "CEP deve ter no máximo 8 dígitos" }).regex(/^\d+$/, { message: "CEP deve conter apenas números" }),
        telefone: z.string().min(11, { message: "Telefone deve ter pelo menos 11 dígitos" }).max(11, { message: "Telefone deve ter no máximo 11 dígitos" }).regex(/^\d+$/, { message: "Telefone deve conter apenas números" }),
        "agente-saude_id": z.number({ required_error: "ID do agente de saúde é obrigatório" }),
    });

    try {
        const { id } = req.params;
        const obj = bodySchema.parse(req.body);

        let agendamento = await knex("agenda").where({ id }).first();

        if (!agendamento) {
            throw new AppError("Agendamento não encontrado", 404);
        }

        await knex("agenda").update(obj).where({ id });

        return res.json({
            message: "Editado Agendamento com sucesso!",
            agendamento: { ...agendamento, ...obj },
        });
    } catch (error) {
        if (error instanceof z.ZodError) {
            const errorMessages = error.errors.map(e => `${e.path.join('.')} - ${e.message}`).join(', ');
            throw new AppError(`Algum erro de escrita: ${errorMessages}`, 400);
        }
        throw new AppError("Não existe esse Agendamento para ser atualizado", 400);
    }
};

export const deleteAgendamento = async (req: Request, res: Response) => {
    try {
        const { id } = req.params;

        let agendamento = await knex("agenda").where({ id }).first();

        if (!agendamento) {
            throw new AppError("Agendamento não encontrado", 404);
        }

        await knex("agenda").where({ id }).delete();

        return res.json({
            message: "Agendamento deletado com sucesso!",
        });
    } catch (error) {
        throw new AppError("Não existe um Agendamento para ser cadastrado", 400);
    }
};
