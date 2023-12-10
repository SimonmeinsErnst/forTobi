package com.example.springboot;

import java.util.ArrayList;
//import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import inginf.Item;
import jakarta.servlet.http.HttpSession;









@Controller
public class ItemController {

    @Autowired private ApplicationContext context;
    AppStore _AppStore;
    AppStore getAppStore() {
        if (_AppStore == null) 
            _AppStore = context.getBean(AppStore.class);
        return _AppStore;
    }

    @PostMapping("/items-gui")
    public String createItem(
        Model model,
        HttpSession session,
        @RequestParam Map<String, String> body
    ) {
        inginf.Item item = new inginf.Item(
            body.get("Nomenclature"),
            body.get("Description"),
            body.get("Material")
        );
        if (body.get("WeightedWeight") != null && body.get("WeightedWeight").length() > 0) 
            item.setWeightedWeight(Integer.parseInt(body.get("WeightedWeight")));
        if (body.get("CalculatedWeight") != null && body.get("CalculatedWeight").length() > 0) 
            item.setCalculatedWeight(Integer.parseInt(body.get("CalculatedWeight")));
        if (body.get("EstimatedWeight") != null && body.get("EstimatedWeight").length() > 0) 
            item.setEstimatedWeight(Integer.parseInt(body.get("EstimatedWeight")));
        getAppStore().addNewItem(item);
        model.addAttribute("id", item.Id);
        return "itemCreated";
    }

    @GetMapping("/items-gui")
    public String createItemDialog() {
        return "itemTemplate";
    }

    @GetMapping("/items-gui/list")
    public String listItems(Model model) {
        model.addAttribute("items", getAppStore().getItemStore());
        return "listItems";
    }

    @GetMapping("/items-gui/{id}/delete")
    public String deleteItem(@PathVariable int id, Model model) {
        model.addAttribute("id", id);
        for (Item item : getAppStore().getItemStore()) 
            if (item.Id == id) {
                getAppStore()
                    .getItemStore()
                    .remove(item);
                break;
            }
        return "itemDeleted";
    }

    @GetMapping("/items-gui/{id}/show")
    public String showItem(@PathVariable int id, Model model) {
        model.addAttribute("id", id);
        for (Item item : getAppStore().getItemStore()) 
            if (item.Id == id) {
                model.addAttribute("item", item);
                break;
            }
        return "showItem";
    }
    /*

    @GetMapping("/items-selektor")
    public String selektor(Model model) {
        List<Item> items = getAppStore().getItemStore();
        model.addAttribute("items", items);
        return "selektor";
    }

    

   



@PostMapping("/items-selektor")
public String assignItem(@RequestParam String itemSelect, @RequestParam String objectSelect, Model model) {
    List<Item> items = getAppStore().getItemStore();

    // Holen Sie die Werte aus den RequestParametern
    String assignment = itemSelect + " enthält " + objectSelect;

    // Fügen Sie das neue Assignment dem Modell hinzu
    model.addAttribute("assignment", assignment);
    model.addAttribute("items", items);

    return "selektor";
}
*/

@GetMapping("/items-selektor")
    public String showSelektor(Model model) {
        List<Item> items = getAppStore().getItemStore();
        model.addAttribute("items", items);

        // Holen Sie die vorhandenen Zuweisungen aus dem Modell
        List<String> fusedAssignments = (List<String>) model.getAttribute("fusedAssignments");

        // Wenn die Liste noch nicht vorhanden ist, erstellen Sie eine neue ArrayList
        if (fusedAssignments == null) {
            fusedAssignments = new ArrayList<>();
        }

        // Setzen Sie die Liste im Modell
        model.addAttribute("fusedAssignments", fusedAssignments);

        return "selektor";
    }

    @PostMapping("/items-selektor")
    public String assignItem(@RequestParam String itemSelect, @RequestParam String objectSelect, Model model) {
        List<Item> items = getAppStore().getItemStore();

        // Holen Sie die vorhandenen Zuweisungen aus dem Modell
        List<String> fusedAssignments = (List<String>) model.getAttribute("fusedAssignments");

        // Wenn die Liste noch nicht vorhanden ist, erstellen Sie eine neue ArrayList
        if (fusedAssignments == null) {
            fusedAssignments = new ArrayList<>();
        }

        // Fügen Sie das neue Assignment hinzu
        String newAssignment = itemSelect + " enthält " + objectSelect;
        fusedAssignments.add(newAssignment);

        // Setzen Sie die aktualisierte Liste im Modell
        model.addAttribute("fusedAssignments", fusedAssignments);
        model.addAttribute("items", items);

        return "selektor";
    }



    @GetMapping("/home")
    public String home(Model model) {
        return "home";
    }

}
