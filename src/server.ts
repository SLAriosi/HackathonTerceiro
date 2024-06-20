import express from "express";
import 'express-async-errors';
import dotenv from "dotenv"
import routes from "./routes";
import AppError from "./utils/AppError";
import { Request, Response, NextFunction } from "express";
import cors from "cors";
const app = express();
dotenv.config();

app.use(express.json()); 

// Liberando acesso a API
app.use(cors({
	origin: '*'
}));

app.use("/api", routes);

app.use((error: Error, req: Request, res: Response, next: NextFunction) => {

    if(error instanceof AppError) {
        return res.status(error.statusCode).json({
            status: "Error",
            message: error.mensagem
        });
    }

    return res.status(500).json({
        status: "Error",
        message: "Ocorreu um erro!",
    });
});

app.listen(process.env.PORT, () => {
    console.log(`Servidor utilizando a porta: ${process.env.PORT}`);
});