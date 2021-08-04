import {request, METHOD} from '@/utils/request'
import { ROOM_RESERVATION ,COMMON_RESERVATION,AVALIABLE_ROOM, SEARCH_USER ,SEARCH_EMPLOYEE,ROOM_RESERVATION_LIST,CONFIRM_ROOM_RESERVATION} from '@/services/api'
import qs from 'qs'

/**
 * 预约会议室服务
 * @returns {Promise<AxiosResponse<T>>}
 */
export async function createRoomReservation(data) {
  console.log(data)
  return request(ROOM_RESERVATION, METHOD.PUT, qs.stringify(data),
    {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
    })
}


/**
 * 变更会议室服务
 * @returns {Promise<AxiosResponse<T>>}
 */
export async function changeRoomReservation(data) {
  return request(ROOM_RESERVATION, METHOD.POST, qs.stringify(data),
    {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
    })
}


/**
 * 取消会议室服务
 * @returns {Promise<AxiosResponse<T>>}
 */
export async function cancelRoomReservation(data) {
  return request(ROOM_RESERVATION, METHOD.DELETE, data)
}


/**
 * 获取会议室详情服务
 * @returns {Promise<AxiosResponse<T>>}
 */
export async function getRoomReservation(data) {
  return request(ROOM_RESERVATION, METHOD.GET, data)
}

/**
 * 获取可用会议室服务
 * @returns {Promise<AxiosResponse<T>>}
 */
export async function avaliableRoom(data) {
  return request(AVALIABLE_ROOM, METHOD.GET, data)
}


/**
 * 模糊搜索用户服务
 * @returns {Promise<AxiosResponse<T>>}
 */
export async function getUserList(data) {
  return request(SEARCH_USER, METHOD.GET, data)
}



/**
 * 预约会议服务
 * @returns {Promise<AxiosResponse<T>>}
 */
export async function createCommonReservation(data) {
  return request(COMMON_RESERVATION, METHOD.PUT, qs.stringify(data),
    {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
    })
}


/**
 * 变更会议服务
 * @returns {Promise<AxiosResponse<T>>}
 */
export async function changeCommonReservation(data) {
  return request(COMMON_RESERVATION, METHOD.POST, qs.stringify(data),
    {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
    })
}


/**
 * 取消会议服务
 * @returns {Promise<AxiosResponse<T>>}
 */
export async function cancelCommonReservation(data) {
  return request(COMMON_RESERVATION, METHOD.DELETE, data)
}


/**
 * 获取会议详情服务
 * @returns {Promise<AxiosResponse<T>>}
 */
export async function getCommonReservation(data) {
  return request(COMMON_RESERVATION, METHOD.GET, data)
}


/** 
 * 模糊搜索企业员工服务
 * @returns {Promise<AxiosResponse<T>>}
 */
export async function getEmployeeList(data) {
  return request(SEARCH_EMPLOYEE, METHOD.GET, data)
}



/** 
 * 获取某个会议室的预约列表
 * @returns {Promise<AxiosResponse<T>>}
 */
export async function getRoomReservationList(data) {
  return request(ROOM_RESERVATION_LIST, METHOD.GET, data)
}


/** 
 * 加入会议室会议验证
 * @returns {Promise<AxiosResponse<T>>}
 */
 export async function confirmRoomReservation(roomMeetingId) {
  return request(CONFIRM_ROOM_RESERVATION, METHOD.GET,{
    reservationId:roomMeetingId
  })
}


export default {
  createRoomReservation,
  changeRoomReservation,
  cancelRoomReservation,
  getRoomReservation,
  createCommonReservation,
  changeCommonReservation,
  cancelCommonReservation,
  getCommonReservation,
  avaliableRoom,
  getUserList,
  getEmployeeList,
  getRoomReservationList,
  confirmRoomReservation
}
