/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> }
 */
exports.up = async function (knex) {
   await knex.schema
      .createTable("idoso", (table) => {
         table.bigIncrements("id");
         table.string("nome", 100).notNullable();
         table.string("telefone", 11).notNullable();
         table.date("data_nascimento").notNullable();
         table.string("cpf", 11).notNullable();
         table.string("cep", 8).notNullable();
         table.integer("numero_casa").notNullable();
         table.string("email", 100).notNullable();
         table.string("rua", 100).notNullable();
         table.string("bairro", 100).notNullable();
         table.string("cidade", 100).notNullable();
         table.string("cartao_sus", 16).notNullable();
         table.text("condicoes");
         table.timestamp("created_at").defaultTo(knex.fn.now());
         table.timestamp("updated_at").defaultTo(knex.fn.now());
      })
      .then(() => {
         console.log("Criada tabela de Idoso");
      });
};

/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> }
 */
exports.down = async function (knex) {
   await knex.schema
      .dropTable("idoso")
      .then((result) => {
         console.log("Deletada tabela de Idoso");
      })
};
