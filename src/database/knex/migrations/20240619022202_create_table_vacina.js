/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> }
 */

//TODO Arrumar essa migration de agendamento_vacina, dando problema com a chave secundária;
//TODO Adicionar Try Catch em todas as Migrations;
exports.up = function(knex) {
   return knex.schema
   .createTable("agendamento_vacina", (table) => {
      table.bigIncrements("id");
      table.integer("vacina_id").unsigned()
      table.foreign("vacina_id").references("vacina.id");
      table.date("data").notNullable();
      table.time("horario").notNullable();
      table.integer("responsavel_idoso").unsigned()
      table.foreign("responsavel_idoso").references("idoso.id");
      table.integer("nome_idoso").unsigned()
      table.foreign("nome_idoso").references("idoso.id");
      table.timestamp("created_at").defaultTo(knex.fn.now());
      table.timestamp("updated_at").defaultTo(knex.fn.now());
   })
   .then(() => {
      console.log("Criada tabela de Agendamento de Vacina");
   })
};

/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> }
 */
exports.down = function(knex) {
   return knex.schema
   .dropTable("agendamento_vacina")
   .then(() => {
      console.log("Deletada tabela de Agendamento de Vacina");
   })
   .catch((err) => {
      console.log(err);
   });
};
