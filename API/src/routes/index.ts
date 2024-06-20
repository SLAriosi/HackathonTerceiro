import { Router } from "express";
import * as vacinaController from "../controllers/vacinaController"
const routes = Router();

routes.get("/vacina", vacinaController.getVacina);
routes.post("/vacina", vacinaController.postVacina);
routes.put("/vacina/:id", vacinaController.putVacina);
routes.delete("/vacina/:id", vacinaController.deleteVacina);


export default routes;
