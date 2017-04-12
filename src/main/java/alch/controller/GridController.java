package alch.controller;

import alch.manager.GridManager;
import alch.manager.GridProcessingManager;
import alch.manager.ProductionPath;
import alch.model.*;
import alch.model.user.User;
import alch.repository.GridRepository;
import alch.request.PipePlaceRequest;
import alch.request.UnitRequest;
import alch.response.TimerStartResponse;
import alch.security.BadRequestException;
import alch.util.AuthUtils;
import alch.util.PipeUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.List;

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

        new GridManager(grid).populateGrid();
        Cell cell = grid.getCells()[pipePlaceRequest.getRow()][pipePlaceRequest.getCol()];

        // find and delete the existing pipes that are already in the directions specified
        if (PipeUtils.isImperfectMatch(cell.pipe1, pipePlaceRequest)) {
            grid.getPipes().remove(cell.pipe1);
        }
        if (PipeUtils.isImperfectMatch(cell.pipe2, pipePlaceRequest)) {
            grid.getPipes().remove(cell.pipe2);
        }

        // create new pipe
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

    @RequestMapping(value = "/api/grid/{gridId}/pipes/{pipeId}/", method = RequestMethod.DELETE)
    public Grid removePipe(@PathVariable Long gridId, @PathVariable Long pipeId) {
        Grid grid = gridRepository.findOne(gridId);

        CollectionUtils.filterInverse(grid.getPipes(), pipe -> { return pipe.getId().equals(pipeId); } );

        gridRepository.save(grid);
        new GridManager(grid).populateGrid();
        return grid;
    }

    @RequestMapping(value = "/api/grid/{gridId}/timer/", method = RequestMethod.POST)
    public ResponseEntity<TimerStartResponse> startProduction(@PathVariable Long gridId) {
        Grid grid = gridRepository.findOne(gridId);

        // if the timer is already running,
        if (grid.getLastTick() != null) {
            throw new BadRequestException();
        }

        TimerStartResponse response = new TimerStartResponse();

        new GridManager(grid).populateGrid();
        GridProcessingManager manager = new GridProcessingManager(grid);
        List<ProductionPath> paths = manager.getPaths();

        if (manager.hasErrors()) {
            response.setCellErrors(manager.getCellErrors());

            // if errors in grid, return error with error messages
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        else {
            grid.setLastTick(Instant.now());

            gridRepository.save(grid);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

}
