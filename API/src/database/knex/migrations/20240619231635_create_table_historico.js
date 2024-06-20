/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> }
 */
exports.up = function(knex) {
   return knex.schema.hasTable("agendamento_vacina").then((exists) => {
     if (!exists) {
       return knex.schema.createTable("agendamento_vacina", (table) => {
         table.bigIncrements("id");
         table.date("data").notNullable();
         table.time("horario").notNullable();
         table.bigInteger("vacina_id").unsigned().notNullable();
         table.bigInteger("idoso_id").unsigned().notNullable();
         table.timestamp("created_at").defaultTo(knex.fn.now());
         table.timestamp("updated_at").defaultTo(knex.fn.now());
         
         table.foreign("vacina_id").references("id").inTable("vacina");
         table.foreign("idoso_id").references("id").inTable("idoso");
       })
       .then(() => {
         console.log("Criada tabela de Agendamento de Vacinas");
      });
     }
   });
 };
 
 exports.down = async function(knex) {
   await knex.schema.dropTable("agendamento_vacina")
   .then((result) => {
      console.log("Deletada tabela de Agendamento de Vacinas");
   })
 };
 

/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> }
 */
exports.down = function(knex) {
  
};
