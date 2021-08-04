import { ROOM_LIST, CREATE_ROOM, DELETE_ROOM, CHANGE_ROOM, EDIT_ROOM } from '@/services/api'
import { request, METHOD } from '@/utils/request'
import qs from 'qs'

/**
 * 获取所有会议室
 * @param {企业ID} companyId 
 * @param {部门ID} departmentId 
 * @returns 
 */
export async function roomList(companyId, departmentId) {
  return request(ROOM_LIST, METHOD.GET, {
    companyId: companyId,
    departmentId: departmentId,
  })
}

/**
 * 增加会议室
 * @param {企业ID} companyId 
 * @param {部门ID} departmentId 
 * @param {会议室名字} roomName 
 * @returns 
 */
export async function createRoom(companyId, departmentId, roomName) {
  return request(CREATE_ROOM, METHOD.POST, qs.stringify({
    companyId: companyId,
    departmentId: departmentId,
    roomName: roomName
  }),
    {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
    })
}

/**
 * 删除会议室
 * @param {会议室ID} roomId 
 * @returns 
 */
export async function deleteRoom(roomId) {
  return request(DELETE_ROOM, METHOD.POST, qs.stringify({
    roomId: roomId
  }),
    {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
    })
}

/**
 * 启用或停用会议室
 * @param {会议室ID} roomId 
 * @param {停用或启用} isActive 
 * @returns 
 */
export async function changeRoom(roomId, isActive) {
  return request(CHANGE_ROOM, METHOD.PUT, qs.stringify({
    roomId: roomId,
    isActive: isActive
  }),
    {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
    })
}

/**
 * 修改会议室名字
 * @param {会议室ID} roomId 
 * @param {会议室名字} roomName 
 * @returns 
 */
 export async function editRoom(roomId,roomName) {
  return request(EDIT_ROOM, METHOD.POST, qs.stringify({
    roomId:roomId,
    roomName: roomName
  }),
    {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
    })
}