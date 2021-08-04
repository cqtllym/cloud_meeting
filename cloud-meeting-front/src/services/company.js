import { CREATE_COMPANY, SEARCH_COMPANY, FETCH_PERSON, INVITE_PERSON, APPLICATION_PERSON, SEARCH_PERSON, DISMISS_COMPANY, QUIT_COMPANY, FETCH_EMPLOYEE, FETCH_ADMIN, SET_ADMIN, DELETE_ADMIN, SEARCH_INFORMATION, SEARCH_COMPANY_INFORMATION, FIRE_PERSON, EDIT_ADDRESS } from '@/services/api'
import { request, METHOD } from '@/utils/request'
import qs from 'qs'

/**
 * 创建企业
 * @param {用户id} user_id 
 * @param {企业名称} company_name 
 * @returns 
 */
export async function createCompany(userId, companyName, companyAddress, companyType) {
  return request(CREATE_COMPANY, METHOD.PUT, qs.stringify({
    companyName: companyName,
    userId: userId,
    companyAddress: companyAddress,
    companyType: companyType
  }),
    {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
    })
}

export async function editAddress(companyId, companyAddress) {
  return request(EDIT_ADDRESS, METHOD.POST, qs.stringify({
    companyId: companyId,
    companyAddress: companyAddress,
  }),
    {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
    })
}

/**
 * 邀请加入企业
 * @param {姓名或联系方式} userId 
 * @param {企业ID} companyId 
 * @returns 
 */
export async function invitePerson(userId, companyId) {
  return request(INVITE_PERSON, METHOD.GET, {
    company_id: companyId,
    user_id: userId,
  })
}

/**
 * 根据用户名或电话模糊查询用户ID
 * @param {用户名或电话号码} name 
 * @returns 用户ID
 */
export async function searchPerson(name) {
  return request(SEARCH_PERSON, METHOD.GET, {
    keyword: name,
  })
}

/**
 * 根据企业ID渲染成员列表
 * @param {企业ID} companyId 
 * @returns 企业成员信息
 */
export async function fetchPerson(companyId) {
  return request(FETCH_PERSON, METHOD.GET, {
    company_id: companyId,
  })
}

/**
 * 在企业中搜索用户
 * @param {搜索的用户名} username 
 * @param {企业ID} companyId 
 * @returns 
 */
export async function searchInformation(username, companyId) {
  return request(SEARCH_INFORMATION, METHOD.GET, {
    userName: username,
    companyId: companyId
  })
}

/**
 * 设置企业管理员
 * @param {用户ID} userId 
 * @param {企业ID} companyId 
 * @returns 
 */
export async function setAdmin(userId, companyId) {
  return request(SET_ADMIN, METHOD.PUT, qs.stringify({
    userId: userId,
    companyId: companyId,
  }),
    {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
    })
}

/**
 * 移除管理员
 * @param {用户ID} userId 
 * @param {企业ID} companyId 
 * @returns 
 */
export async function deleteAdmin(userId, companyId) {
  return request(DELETE_ADMIN, METHOD.PUT, qs.stringify({
    userId: userId,
    companyId: companyId,
  }),
    {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
    })
}

/**
 * 企业员工（非管理员
 * @param {企业ID} companyId 
 * @returns 
 */
export async function fetchEmployee(companyId) {
  return request(FETCH_EMPLOYEE, METHOD.GET, {
    companyId: companyId,
  })
}

/**
 * 企业管理员
 * @param {企业ID} companyId 
 * @returns 
 */
export async function fetchAdmin(companyId) {
  return request(FETCH_ADMIN, METHOD.GET, {
    companyId: companyId,
  })
}

/**
 * 渲染公司列表
 * @param {公司名称} companyName 
 * @returns 公司信息
 */
export async function searchCompany(companyName) {
  return request(SEARCH_COMPANY, METHOD.GET, {
    name: companyName,
  })
}

/**
 * 申请加入企业
 * @param {用户ID} userId 
 * @param {企业ID} companyId 
 * @returns 
 */
export async function joinCompany(userId, companyId) {
  return request(APPLICATION_PERSON, METHOD.GET, {
    user_id: userId,
    company_id: companyId,
  })
}

/**
 * 解散企业
 * @param {企业ID} companyId 
 * @returns 
 */
export async function dismissCompany(companyId) {
  return request(DISMISS_COMPANY, METHOD.POST, qs.stringify({
    companyId: companyId,
  }),
    {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
    })
}

/** 
 * 退出企业
 * @param {用户id} userId 
 * @param {企业id} companyId
 * @returns 
 */
export async function quitCompany(id, cid) {
  return request(QUIT_COMPANY, METHOD.PUT, qs.stringify({
    userId: id,
    companyId: cid,
  }),
    {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
    })
}

/**
 * 显示企业信息
 * @param {用户ID} userId 
 * @returns 
 */
export async function searchCompanyInformation(userId) {
  return request(SEARCH_COMPANY_INFORMATION, METHOD.GET, {
    userId: userId,
  })
}

/**
 * 企业解雇用户
 * @param {企业ID} companyId 
 * @param {用户ID列表} userIdList 
 * @returns 
 */
export async function firePerson(companyId, userIdList) {
  return request(FIRE_PERSON, METHOD.POST, qs.stringify({
    companyId: companyId,
    userIdList: userIdList,
  }),
    {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
    })
}