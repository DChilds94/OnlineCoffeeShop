package controllers;

import db.DBHelper;
import models.stock.Stock;
import models.stock.StockType;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static spark.Spark.get;

public class EquipmentController {

    public EquipmentController() {
        setUpEndPoints();
    }

    private void setUpEndPoints() {

        get("/equipment", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            List<Stock> allStock = DBHelper.getAll(Stock.class);
            List<Stock> equipment = new ArrayList<>();
            for (Stock item : allStock) {
                if (item.getType() == StockType.EQUIPMENT) {
                    equipment.add(item);
                }
            }
            model.put("equipment", equipment);
            model.put("template", "templates/equipment/index.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        get("/equipment/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            Stock item = DBHelper.find(Stock.class, id);
            HashMap<String, Object> model = new HashMap<>();
            model.put("item", item);
            model.put("template", "templates/equipment/show.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());
    }
}
