import { USER_NOTICE, DEAL_INVITATION, COMPANY_NOTICE, DEAL_APPLICATION, DEAL_READ, UNREAD_NOTICE } from '@/services/api'
import { request, METHOD } from '@/utils/request'
import qs from 'qs'

/**
 * 渲染用户端的消息列表
 * @param {用户ID} userId 
 * @returns 
 */
export async function userNotice(userId) {
  return request(USER_NOTICE, METHOD.GET, {
    userId: userId,
  })
}

/**
 * 用户处理邀请信息
 * @param {通知ID} noticeId 
 * @param {处理结果} result 
 * @returns 
 */
export async function dealInvitation(noticeId, result) {
  return request(DEAL_INVITATION, METHOD.POST, qs.stringify({
    noticeId: noticeId,
    result: result,
  }),
    {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
    })
}

/**
 * 渲染管理员收到的消息
 * @param {公司ID} companyId 
 * @returns 
 */
export async function companyNotice(companyId) {
  return request(COMPANY_NOTICE, METHOD.GET, {
    companyId: companyId,
  })
}

/**
 * 管理员处理申请信息
 * @param {通知ID} noticeId 
 * @param {处理结果} result 
 * @returns 
 */
export async function dealApplication(noticeId, result) {
  return request(DEAL_APPLICATION, METHOD.POST, qs.stringify({
    noticeId: noticeId,
    result: result,
  }),
    {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
    })
}

/**
 * 处理已读消息
 * @param {消息ID} noticeId 
 * @param {处理结果} result 
 * @returns 
 */
export async function dealRead(noticeId, result) {
  return request(DEAL_READ, METHOD.PUT, qs.stringify({
    noticeId: noticeId,
    result: result,
  }),
    {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
    })
}

/**
 * 获取用户未读消息数
 * @param {用户ID} userId 
 * @returns 
 */
export async function unreadNotice(userId) {
  return request(UNREAD_NOTICE, METHOD.GET, {
    userId: userId,
  })
}


