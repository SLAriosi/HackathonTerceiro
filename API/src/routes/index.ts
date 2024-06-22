import { Router } from "express";
import * as vacinaController from "../controllers/vacinaController";
import * as agendamentoController from "../controllers/agendamentoController";
import * as agenteSaudeController from "../controllers/agenteController";

const routes = Router();

routes.get("/vacina", vacinaController.getVacina);
routes.post("/vacina", vacinaController.postVacina);
routes.put("/vacina/:id", vacinaController.putVacina);
routes.delete("/vacina/:id", vacinaController.deleteVacina);

routes.get("/agenda", agendamentoController.getAgendamento);
routes.post("/agenda", agendamentoController.postAgendamento);
routes.put("/agenda/:id", agendamentoController.putAgendamento);
routes.delete("/agenda/:id", agendamentoController.deleteAgendamento);

routes.get("/agente", agenteSaudeController.getAgente);

export default routes;
