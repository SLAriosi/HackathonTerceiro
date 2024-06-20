/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> }
 */
exports.up = async function (knex) {
    await knex.schema.hasTable("agente-saude").then((exists) => {
        if (!exists) {
            return knex.schema.createTable("agente-saude", (table) => {
                table.bigIncrements("id");
                table.string("nome", 100).notNullable();
                table.string("username", 100).notNullable();
                table.string("password", 100).notNullable();
                table.timestamp("created_at").defaultTo(knex.fn.now());
                table.timestamp("updated_at").defaultTo(knex.fn.now());
            })
                .then(() => {
                    console.log("Criada tabela de Agente de Saúde");
                });
        }
    });
};
/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> }
 */

exports.down = async function (knex) {
    await knex.schema.dropTable("agente-saude")
        .then((result) => {
            console.log("Deletada tabela de Agente de Saúde");
        })
};

