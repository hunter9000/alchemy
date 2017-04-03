package alch.controller;

import alch.manager.GridManager;
import alch.model.*;
import alch.model.user.User;
import alch.repository.GridRepository;
import alch.request.PipePlaceRequest;
import alch.request.UnitRequest;
import alch.security.BadRequestException;
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
        new GridManager(grid).populateGrid();
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

    @RequestMapping(value = "/api/grid/{gridId}/units/", method = RequestMethod.POST)
    public Grid purchaseUnit(@PathVariable Long gridId, @RequestBody UnitRequest unitRequest) {
        Grid grid = gridRepository.findOne(gridId);

//        for (Unit unit : grid.getUnits()) {
//            if (unit.getId().equals(unitRequest.getId())) {

        Unit unit = new GridManager(grid).getUnit(unitRequest.getId());
        if (unit == null) {
            throw new BadRequestException(/*"unit id invalid"*/);
        }

        ResourceInventory inv = grid.getResourceInventory().get(unit.getCostResourceType());
        inv.setAmount(inv.getAmount() - unit.getCostAmount());
        unit.setPurchased(true);

        gridRepository.save(grid);
        new GridManager(grid).populateGrid();
        return grid;
//            }
//        }
    }

    @RequestMapping(value = "/api/grid/{gridId}/units/", method = RequestMethod.PUT)
    public Grid placeUnit(@PathVariable Long gridId, @RequestBody UnitRequest unitRequest) {
        Grid grid = gridRepository.findOne(gridId);

        Unit unit = new GridManager(grid).getUnit(unitRequest.getId());
        if (unit == null) {
            throw new BadRequestException(/*"unit id invalid"*/);
        }

        unit.setCol(unitRequest.getCol());
        unit.setRow(unitRequest.getRow());

        gridRepository.save(grid);
        new GridManager(grid).populateGrid();
        return grid;
    }

    @RequestMapping(value = "/api/grid/{gridId}/pipes/", method = RequestMethod.POST)
    public Grid placePipe(@PathVariable Long gridId, @RequestBody PipePlaceRequest pipePlaceRequest) {
        Grid grid = gridRepository.findOne(gridId);

        // find and delete the existing pipes that are already in the directions specified
        new GridManager(grid).populateGrid();
        Cell cell = grid.getCells()[pipePlaceRequest.getRow()][pipePlaceRequest.getCol()];
        if (cell.pipe1 != null) {
            if (cell.pipe1.matchesDirection(pipePlaceRequest.getDir1()) || cell.pipe1.matchesDirection(pipePlaceRequest.getDir2())) {
                grid.getPipes().remove(cell.pipe1);
            }
            if (cell.pipe2.matchesDirection(pipePlaceRequest.getDir1()) || cell.pipe2.matchesDirection(pipePlaceRequest.getDir2())) {
                grid.getPipes().remove(cell.pipe2);
            }
        }

        Pipe newPipe = new Pipe();
        newPipe.setGrid(grid);
        newPipe.setCol(pipePlaceRequest.getCol());
        newPipe.setRow(pipePlaceRequest.getRow());
        newPipe.setInDirection(pipePlaceRequest.getDir1());
        newPipe.setOutDirection(pipePlaceRequest.getDir2());
        grid.getPipes().add(newPipe);

        gridRepository.save(grid);
        new GridManager(grid).populateGrid();
        return grid;
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
