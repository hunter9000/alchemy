package alch.controller;

import alch.manager.GridManager;
import alch.model.Grid;
import alch.model.ResourceInventory;
import alch.model.Unit;
import alch.model.user.User;
import alch.repository.GridRepository;
import alch.request.UnitRequest;
import alch.util.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class GridController {

    @Autowired
    private GridRepository gridRepository;

    @Autowired
    private HttpServletRequest request;

//    @Autowired
//    private Store store;

    @RequestMapping(value = "/api/grid/", method = RequestMethod.POST)
    public Grid createGrid() {
        User user = AuthUtils.getLoggedInUser(request);
        Grid grid = GridManager.createGrid(user);

        gridRepository.save(grid);

        return grid;
    }

    @RequestMapping(value = "/api/grid/{gridId}/", method = RequestMethod.GET)
    public Grid getGrid(@PathVariable Long gridId) {
        Grid grid = gridRepository.findOne(gridId);
        GridManager.populateGrid(grid);
        return grid;
    }

    @RequestMapping(value = "/api/grid/", method = RequestMethod.GET)
    public Iterable<Grid> getAllGrids() {
        User user = AuthUtils.getLoggedInUser(request);

        return gridRepository.findAllByOwner(user);
    }

    @RequestMapping(value = "/api/grid/{gridId}/", method = RequestMethod.DELETE)
    public void deleteGrid(@PathVariable Long gridId) {
        Grid grid = gridRepository.findOne(gridId);
        gridRepository.delete(grid);
    }

    @RequestMapping(value = "/api/grid/{gridId}/units/", method = RequestMethod.PUT)
    public void purchaseUnit(@PathVariable Long gridId, @RequestBody UnitRequest unitRequest) {
        Grid grid = gridRepository.findOne(gridId);

        for (Unit unit : grid.getUnits()) {
            if (unit.getId().equals(unitRequest.getId())) {
                ResourceInventory inv = grid.getResourceInventory().get(unit.getCostResourceType());
                inv.setAmount(inv.getAmount() - unit.getCostAmount());
                unit.setPurchased(true);
                gridRepository.save(grid);
                return;
            }
        }
    }

//    /** Get all the purchasables that this grid has either available, or hinted at. */
//    @RequestMapping(value = "/api/grid/{gridId}/store/", method = RequestMethod.GET)
//    public List<Unit> getPurchasableUnits(@PathVariable Long gridId) {
//        Grid grid = gridRepository.findOne(gridId);
//
//        List<PurchasableUnit> units = store.getAllPurchasableUnits();
//        List<PurchasableUnit> retList = new ArrayList<>();
//
//        for (PurchasableUnit purchasable : units) {
//            for (PurchasedUnit purchased : grid.getPurchasedUnits()) {
//                if (purchased.getPurchasableUnitType().equals(purchasable.getPurchasableUnitType())) {
//                    continue;       // don't add it to the list if already purchased
//                }
//            }
//            // if it isn't close enough to being affordable, don't add it
//            ResourceInventory inventory = grid.getResourceInventory().get(purchasable.getCostResourceType());
//            // if player has 75% of the cost, show it in the list
//            if (inventory.getAmount() >= purchasable.getCostAmount() * .75) {
//                retList.add(purchasable);
//
//            }
//        }
//
//
//
//        return units;
//    }

}
