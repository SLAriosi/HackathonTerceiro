import { Router } from "express";
import * as vacinaController from "../controllers/vacinaController"
import * as agendamentoController from "../controllers/agendamentoController"
const routes = Router();

routes.get("/vacina", vacinaController.getVacina);
routes.post("/vacina", vacinaController.postVacina);
routes.put("/vacina/:id", vacinaController.putVacina);
routes.delete("/vacina/:id", vacinaController.deleteVacina);

routes.get("/agendamento", agendamentoController.getAgendamento);
routes.post("/agendamento", agendamentoController.postAgendamento);
routes.put("/agendamento/:id", agendamentoController.putAgendamento);
routes.delete("/agendamento/:id", agendamentoController.deleteAgendamento);

export default routes;
