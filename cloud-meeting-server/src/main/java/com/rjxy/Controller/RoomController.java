package com.rjxy.Controller;

import com.rjxy.Entity.Room;
import com.rjxy.Enum.ErrorEnum;
import com.rjxy.Results.Result;
import com.rjxy.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    RoomService roomService;

    @PostMapping("/setup")
    public Result<Room> setupRoom(@RequestParam Integer companyId,
                                  @RequestParam(required = false) Integer departmentId,
                                  @RequestParam String roomName) {
        return Result.success(roomService.setupRoom(companyId, departmentId, roomName));
    }

    @GetMapping("/all_room_list")
    public Result<List<Room>> getRoomList(@RequestParam Integer companyId,
                                          @RequestParam(required = false) Integer departmentId) {
        if (companyId != null)
            return Result.success(roomService.getRoomList(companyId, departmentId));
        else
            return Result.error(ErrorEnum.IllegalNullFieldError);
    }

    @GetMapping("/available_room_list")
    public Result<List<Room>> getAvailableRoomList(@RequestParam Integer companyId,
                                                   @RequestParam(required = false) Integer departmentId) {
        if (companyId != null)
            return Result.success(roomService.getAvailableRoomList(companyId, departmentId));
        else
            return Result.error(ErrorEnum.IllegalNullFieldError);

    }

    @PutMapping("/change_room_active")
    public Result<String> changeActive(@RequestParam Integer roomId,
                                       @RequestParam Boolean isActive) {
        Boolean exception;
        if (isActive)
            exception = roomService.setRoomActive(roomId);
        else
            exception = roomService.setRoomInactive(roomId);

        if (exception)
            return Result.success("ć´ćšćĺ");
        else
            return Result.error(ErrorEnum.RoomActiveChangeError);
    }

    @PostMapping("/modify")
    public Result<String> modify(@RequestParam Integer roomId,
                                 @RequestParam String roomName) {

        if (roomService.modifyRoom(roomId, roomName))
            return Result.success("äżŽćšäźčŽŽäżĄćŻćĺ");
        else return Result.error(ErrorEnum.RoomModifyError);
    }

    @PostMapping("/delete")
    public Result<String> delete(@RequestParam Integer roomId) {
        if (roomService.deleteRoom(roomId))
            return Result.success("ĺ é¤äźčŽŽĺŽ¤ćĺ");
        else
            return Result.error(ErrorEnum.RoomDeleteError);
    }
}