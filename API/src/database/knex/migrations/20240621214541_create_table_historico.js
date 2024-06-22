/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> }
 */
exports.up = async function (knex) {
    await knex.schema.hasTable("historico").then((exists) => {
        if (!exists) {
            return knex.schema.createTable("historico", (table) => {
                table.bigIncrements("id");

                table.bigInteger("idoso_id").unsigned().notNullable();
                table.foreign("idoso_id").references("id").inTable("idoso");

                table.bigInteger("agenda_id").unsigned().notNullable();
                table.foreign("agenda_id").references("id").inTable("agenda");

                table.bigInteger("vacina_id").unsigned().notNullable();
                table.foreign("vacina_id").references("id").inTable("vacina");

                table.timestamp("created_at").defaultTo(knex.fn.now());
                table.timestamp("updated_at").defaultTo(knex.fn.now());
            })
                .then(() => {
                    console.log("Criada tabela de Historico");
                });
        }
    });
};
/**
* @param { import("knex").Knex } knex
* @returns { Promise<void> }
*/

exports.down = async function (knex) {
    await knex.schema.dropTable("historico")
        .then((result) => {
            console.log("Deletada tabela de Historico");
        })
};