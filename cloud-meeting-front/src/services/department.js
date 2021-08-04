import { DEPARTMENT_LIST, UPDATE_DEPARTMENT, FIND_EMPLOYEE, DELETE_DEPARTMENT, CREATE_DEPARTMENT, EDIT_DEPARMENT } from '@/services/api'
import { request, METHOD } from '@/utils/request'
import qs from 'qs'

/**
 * 获取企业部门
 * @param {企业ID} companyId 
 * @returns 
 */
export async function departmentList(companyId) {
  return request(DEPARTMENT_LIST, METHOD.GET, {
    companyId: companyId,
  })
}

/**
 * 更改企业用户部门
 * @param {用户Id} userId 
 * @param {部门Id} departmentId 
 * @param {企业Id} companyId 
 * @returns 
 */
export async function upadteDepartment(userId, departmentId, companyId) {
  return request(UPDATE_DEPARTMENT, METHOD.POST, qs.stringify({
    userId: userId,
    departmentId: departmentId,
    companyId: companyId
  }),
    {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
    })
}

/**
 * 查找对应部门的人员
 * @param {部门Id} departmentId 
 * @param {企业Id} companyId 
 * @returns 
 */
export async function findEmployee(departmentId, companyId) {
  return request(FIND_EMPLOYEE, METHOD.GET, {
    departmentId: departmentId,
    companyId: companyId,
  })
}

/**
 * 删除部门
 * @param {部门Id} departmentId 
 * @returns 
 */
export async function deleteDerpartment(departmentId) {
  return request(DELETE_DEPARTMENT, METHOD.DELETE, {
    departmentId: departmentId,
  })
}

/** 
 * 添加部门
 * @param {企业id} companyId 
 * @param {部门名称} departmentName 
 * @returns 
 */
export async function createDepartment(companyId, departmentName) {
  return request(CREATE_DEPARTMENT, METHOD.PUT, qs.stringify({
    companyId: companyId,
    departmentName: departmentName,
  }),
    {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
    })
}

/**
 * 修改部门名称
 * @param {部门Id} departmentId 
 * @param {部门名称} departmentName 
 * @returns 
 */
export async function editDepartment(departmentId, departmentName) {
  return request(EDIT_DEPARMENT, METHOD.POST, qs.stringify({
    departmentId: departmentId,
    departmentName: departmentName,
  }),
    {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
    })
}
