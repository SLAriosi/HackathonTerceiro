/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> }
 */
exports.up = async function (knex) {
    await knex.schema.hasTable("agenda").then((exists) => {
        if (!exists) {
            return knex.schema.createTable("agenda", (table) => {
                table.bigIncrements("id");
                table.date("data").notNullable();
                table.time("horario").notNullable();
                table.bigInteger("agente-saude_id").unsigned().notNullable();
                table.foreign("agente-saude_id").references("id").inTable("agente-saude");
                table.string("vacina", 100).notNullable();
                table.string("nome", 100).notNullable();
                table.string("cpf", 11).notNullable();
                table.string("cep", 8).notNullable();
                table.string("telefone", 11).notNullable();

                table.timestamp("created_at").defaultTo(knex.fn.now());
                table.timestamp("updated_at").defaultTo(knex.fn.now());
            })
                .then(() => {
                    console.log("Criada tabela de Agenda");
                });
        }
    });
};
/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> }
 */

exports.down = async function (knex) {
    await knex.schema.dropTable("agenda")
        .then((result) => {
            console.log("Deletada tabela de Agenda");
        })
};