/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> }
 */
exports.up = async function(knex) {
   await knex.schema
   .createTable("vacina", (table) => {
      table.bigIncrements("id");
      table.string("nome", 100).notNullable();
      table.date("data_campanha_inicio").notNullable();
      table.date("data_campanha_fim").notNullable();
      table.timestamp("created_at").defaultTo(knex.fn.now());
      table.timestamp("updated_at").defaultTo(knex.fn.now());
   })
   .then(() => {
      console.log("Criada tabela de Vacina");
   });
};

/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> }
 */
exports.down = async function(knex) {
   await knex.schema.dropTable("vacina")
   .then((result) => {
      console.log("Deletada tabela de Vacina");
   })
};
