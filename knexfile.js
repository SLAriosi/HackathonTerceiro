const path = require("path");
const dotenv = require("dotenv");
dotenv.config();

module.exports = {
    development: {
        client: "mysql",
        connection: {
            host: process.env.DB_HOST,
            port: Number(process.env.DB_PORT),
            user: process.env.DB_USER,
            database: process.env.DB_NAME,
            password: process.env.DB_PASSWORD,
        },
        migrations: {
            directory: path.resolve(
                __dirname,
                "src",
                "database",
                "knex",
                "migrations"
            ),
        },
        useNullAsDefault: true,
    },
};
