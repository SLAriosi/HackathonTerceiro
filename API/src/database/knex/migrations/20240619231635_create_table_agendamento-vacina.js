exports.up = function (knex) {
  return knex.schema.hasTable("agendamento_vacina").then((exists) => {
    if (!exists) {
      return knex.schema.createTable("agendamento_vacina", (table) => {
        table.bigIncrements("id");
        table.date("data").notNullable();
        table.string("horario").notNullable();
        table.string("vacina", 100).notNullable();
        table.string("nome", 100).notNullable();
        table.string("cpf", 11).notNullable();
        table.string("cep", 8).notNullable();
        table.string("telefone", 11).notNullable();
        table.timestamp("created_at").defaultTo(knex.fn.now());
        table.timestamp("updated_at").defaultTo(knex.fn.now());
      })
        .then(() => {
          console.log("Criada tabela de Agendamento de Vacinas");
        });
    }
  });
};

exports.down = async function (knex) {
  await knex.schema.dropTable("agendamento_vacina")
    .then((result) => {
      console.log("Deletada tabela de Agendamento de Vacinas");
    })
};