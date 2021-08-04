<style lang="less">
@import "index";
</style>

<template>
  <a-card style="padding: 0">
    <a-tabs ref="container">
      <a-tab-pane tab="正在进行" key="1">
        <div class="meeting-loading">
          <div id="return-message" class="meeting-loading-containers">
            <div
              class="the-meeting-loading-container"
              style="height: auto; padding-bottom: 10px"
              v-for="(item, index) in List.holding_meeting_list"
              v-bind:key="index"
            >
              <div class="meeting-head">
                <div class="meeting-head-message">
                  <i class="meeting-picture">
                    <svg
                      viewBox="0 0 24 24"
                      width="1em"
                      height="1em"
                      fill="currentColor"
                      aria-hidden="true"
                      focusable="false"
                    >
                      <path
                        d="M7.273 14.4h9.455a.5.5 0 000-1H7.273a.5.5 0 000 1zM7.275 17.6h4.651a.5.5 0 000-1H7.275a.5.5 0 000 1z"
                      ></path>
                      <path
                        d="M19 4.9h-2.9v1H19a1 1 0 011 1V10H4V6.9a1 1 0 011-1h3.1v-1H5a2 2 0 00-2 2V19a2 2 0 002 2h14a2 2 0 002-2V6.9a2 2 0 00-2-2zM20 19a1 1 0 01-1 1H5a1 1 0 01-1-1v-8h16z"
                      ></path>
                      <path
                        d="M11.1 4.9h2v1h-2zM14.1 7.2a.5.5 0 001 0V3.5a.5.5 0 00-1 0v2.4zM9.1 7.2a.5.5 0 001 0V3.5a.5.5 0 00-1 0v2.4z"
                      ></path>
                    </svg>
                  </i>
                  <span class="meeting-subject-style">
                    <span class="meeting-subject">{{ item.met_sub }}</span>
                  </span>
                  <span class="meeting-initiator-style">
                    <span class="meeting-initiator"
                      >{{ item.met_org }}发起的{{ item.met_type }}</span
                    >
                  </span>
                  <span
                    class="meeting-time-remainding-style"
                    :style="{ color: item.met_alert_color }"
                  >
                    <svg
                      :hidden="!item.before"
                      viewBox="0 0 24 24"
                      width="18px"
                      height="18px"
                      fill="currentColor"
                      aria-hidden="true"
                      focusable="false"
                    >
                      <path
                        d="M8.92 2.74h6.17a.72.72 0 000-1.44H8.92a.72.72 0 000 1.44zM16 15.77l-3.17-1.82-.62-5.59a1.06 1.06 0 00-.4-.72l-.51-.44v7.92L16.7 17l-.21-.61a1.05 1.05 0 00-.49-.62z"
                      ></path>
                      <path
                        d="M12.1 4.1a9.1 9.1 0 109.1 9.1 9.1 9.1 0 00-9.1-9.1zm0 16.8a7.7 7.7 0 117.7-7.7 7.71 7.71 0 01-7.7 7.7z"
                      ></path>
                      <path
                        d="M20.49 7.13a1 1 0 00-1.41-1.41l-.45.45a9.63 9.63 0 011.32 1.51z"
                      ></path>
                    </svg>
                    <svg
                      :hidden="item.before"
                      viewBox="0 0 24 24"
                      width="18px"
                      height="18px"
                      fill="currentColor"
                      aria-hidden="true"
                      focusable="false"
                    >
                      <path
                        d="M15.35 14.43h-.28c-.28 0-.62-.08-.62-.45v-.38a3.42 3.42 0 001.05-1.42 1.26 1.26 0 00.33-.19 1.28 1.28 0 00.47-.87l.16-1.52a1.28 1.28 0 00-.27-.93 1.3 1.3 0 00-.24-.24L16 7a.7.7 0 00-.17-.5l-.57-.66V4.7a.7.7 0 00-.7-.68h-1.73a5.23 5.23 0 00-4.39 2.1l-.31.44A.7.7 0 008 7l.1 1.55a1.3 1.3 0 00-.23.22 1.28 1.28 0 00-.27.93l.16 1.53a1.27 1.27 0 00.47.86 1.33 1.33 0 00.36.21 3.5 3.5 0 00.18.35 3.45 3.45 0 00.82.94V14c0 .37-.34.45-.62.45h-.29a4.75 4.75 0 00-4.41 3.73l-.14 1a.7.7 0 00.69.79H19.2a.7.7 0 00.69-.79l-.15-1.09a4.74 4.74 0 00-4.39-3.66zM5.63 18.6v-.2a3.32 3.32 0 013-2.56H9A1.86 1.86 0 0011 14v-.75a.7.7 0 00-.33-.6 2 2 0 01-.68-.7 2 2 0 01-.2-.47.7.7 0 00-.38-.48.69.69 0 00-.27 0L9 9.68A.7.7 0 009.52 9l-.11-1.83.17-.23a3.88 3.88 0 013.26-1.5h1v.7a.7.7 0 00.17.44l.55.63-.1 1.64a.69.69 0 00.17.52.67.67 0 00.37.21v1.28a.7.7 0 00-.67.53 2 2 0 01-.91 1.25.7.7 0 00-.33.6V14a1.86 1.86 0 002 1.85h.28a3.32 3.32 0 013 2.52v.25z"
                      ></path>
                      <path
                        d="M22.59 16.4a3.59 3.59 0 00-3.38-2.83H19c-.26 0-.26-.1-.26-.13v-.19a2.67 2.67 0 00.72-1 1.13 1.13 0 00.21-.14 1.12 1.12 0 00.41-.76l.11-1.1a1.13 1.13 0 00-.37-1l.06-.94a.7.7 0 00-.17-.5l-.37-.43v-.76a.7.7 0 00-.7-.68h-1.26a.7.7 0 000 1.4H18v.32a.7.7 0 00.17.44l.34.4-.07 1.11a.68.68 0 00.17.52.72.72 0 00.21.16l-.07.64a.7.7 0 00-.47.5 1.3 1.3 0 01-.57.79.7.7 0 00-.33.6v.54A1.55 1.55 0 0019 15h.2a2.2 2.2 0 012 1.65.7.7 0 00.2 1.35h.6a.7.7 0 00.69-.79zM2.78 16.63a2.21 2.21 0 012-1.65H5a1.55 1.55 0 001.66-1.53v-.55a.7.7 0 00-.33-.6 1.3 1.3 0 01-.57-.79.73.73 0 00-.47-.47l-.07-.68a.7.7 0 00.38-.66l-.1-1.11.34-.4A.7.7 0 006 7.76v-.33h.6A.7.7 0 006.6 6H5.34a.7.7 0 00-.7.68v.76l-.37.43a.7.7 0 00-.17.5l.06.94a1.12 1.12 0 00-.37 1l.11 1.11a1.12 1.12 0 00.41.76 1.16 1.16 0 00.21.14 2.66 2.66 0 00.72 1v.19s0 .13-.26.13h-.2a3.6 3.6 0 00-3.39 2.87l-.1.76A.7.7 0 002 18h.6a.7.7 0 00.2-1.37z"
                      ></path>
                    </svg>
                    <span class="meeting-time-remainding">{{
                      item.met_time_rem
                    }}</span>
                  </span>
                </div>
                <!-- <div
                  class="meeting-head-operation"
                  :hidden="!item.show_editor_and_cancel"
                >
                  <span class="operation-editor">
                    <a
                      class="meeting-editor"
                      type="button"
                      @click="editor_meeting(item.met_number, item.met_id)"
                    >
                      <svg
                        viewBox="0 0 24 24"
                        width="1em"
                        height="1em"
                        fill="currentColor"
                        aria-hidden="true"
                        focusable="false"
                      >
                        <path
                          d="M9.82 13.94l3-.28L20 6.53a1.56 1.56 0 000-2.2l-.55-.55a1.56 1.56 0 00-2.2 0l-7.15 7.14zm1.63-2.39l6.79-6.79a.16.16 0 01.22 0l.55.55a.16.16 0 010 .22l-6.78 6.78-.86.08z"
                        ></path>
                        <path
                          d="M19.6 11.94V19a.6.6 0 01-.6.6H5a.6.6 0 01-.6-.6V5a.6.6 0 01.6-.6h6.9V3H5a2 2 0 00-2 2v14a2 2 0 002 2h14a2 2 0 002-2v-7.06z"
                        ></path>
                      </svg>
                      编辑
                    </a>
                  </span>
                  <span class="operation-cancel">
                    <a
                      class="meeting-cancel"
                      type="button"
                      @click="cancel_meeting(item.met_id, item.met_number)"
                    >
                      <svg
                        viewBox="0 0 24 24"
                        width="1em"
                        height="1em"
                        fill="currentColor"
                        aria-hidden="true"
                        focusable="false"
                      >
                        <path
                          d="M16.64 7.9H4.83L7.74 5a.7.7 0 00-1-1L2 8.74l4.74 4.74a.7.7 0 101-1L4.55 9.3h12.09a4.6 4.6 0 010 9.2H8.4a.7.7 0 100 1.4h8.24a6 6 0 000-12z"
                        ></path>
                      </svg>
                      取消
                    </a>
                  </span>
                </div>  -->
              </div>
              <div class="meeting-body">
                <div class="meeting-body-information" style="min-height: 220px">
                  <div class="meeting-body-messages">
                    <div
                      class="the-message"
                      v-if="
                        item.met_type == '预约会议' ||
                        item.met_type == '预约会议室会议'
                      "
                    >
                      <label class="message-label">预约时间</label>
                      <span class="message-information">{{
                        item.met_time
                      }}</span>
                    </div>
                    <div class="the-message" v-if="item.met_type=='预约会议'">
                      <label class="message-label">开始时间</label>
                      <span class="message-information">{{
                        item.met_begin_time
                      }}</span>
                    </div>
                    <div class="the-message">
                      <label class="message-label">会议号</label>
                      <span class="message-information">{{
                        item.met_number
                      }}</span>
                    </div>
                    <div class="the-message" v-if="item.met_type=='预约会议室会议'">
                      <label class="message-label">会议室</label>
                      <span class="message-information">{{
                        item.met_room_name
                      }}</span>
                    </div>
                    <div class="the-message">
                      <label class="message-label">发起人</label>
                      <span class="message-information">{{
                        item.met_org
                      }}</span>
                    </div>
                    <div class="the-message">
                      <label class="message-label">参会人</label>
                      <span class="message-information">
                        <span class="message-information">{{
                          item.met_eglist
                        }}</span>
                        <a
                          type="button"
                          @click="showAttendee(item.met_list, item.met_number)"
                          class="message-information_show_more"
                          >查看更多</a
                        >
                      </span>
                    </div>
                  </div>
                  <div class="meeting-body-options">
                    <a-button
                      v-if="item.met_type == '预约会议室会议'"
                      @click="start_room_meeting(item.met_number)"
                      :id="'start-meeting(' + item.met_id + ')'"
                      class="button-start"
                      >加入会议</a-button
                    >
                    <a-button
                      v-else
                      @click="start_meeting(item.met_number)"
                      :id="'start-meeting(' + item.met_id + ')'"
                      class="button-start"
                      >加入会议</a-button
                    >
                  </div>
                </div>
              </div>
            </div>
            <a-empty
              v-if="
                !List.holding_meeting_list ||
                List.holding_meeting_list.length == 0
              "
            />
          </div>
        </div>
      </a-tab-pane>
      <a-tab-pane tab="预约会议" key="2">
        <div class="meeting-loading">
          <div id="return-message" class="meeting-loading-containers">
            <div
              class="the-meeting-loading-container"
              v-for="(item, index) in List.reserved_meeting_list"
              v-bind:key="index"
            >
              <div class="meeting-head">
                <div class="meeting-head-message">
                  <i class="meeting-picture">
                    <svg
                      viewBox="0 0 24 24"
                      width="1em"
                      height="1em"
                      fill="currentColor"
                      aria-hidden="true"
                      focusable="false"
                    >
                      <path
                        d="M7.273 14.4h9.455a.5.5 0 000-1H7.273a.5.5 0 000 1zM7.275 17.6h4.651a.5.5 0 000-1H7.275a.5.5 0 000 1z"
                      ></path>
                      <path
                        d="M19 4.9h-2.9v1H19a1 1 0 011 1V10H4V6.9a1 1 0 011-1h3.1v-1H5a2 2 0 00-2 2V19a2 2 0 002 2h14a2 2 0 002-2V6.9a2 2 0 00-2-2zM20 19a1 1 0 01-1 1H5a1 1 0 01-1-1v-8h16z"
                      ></path>
                      <path
                        d="M11.1 4.9h2v1h-2zM14.1 7.2a.5.5 0 001 0V3.5a.5.5 0 00-1 0v2.4zM9.1 7.2a.5.5 0 001 0V3.5a.5.5 0 00-1 0v2.4z"
                      ></path>
                    </svg>
                  </i>
                  <span class="meeting-subject-style">
                    <span class="meeting-subject">{{ item.met_sub }}</span>
                  </span>
                  <span class="meeting-initiator-style">
                    <span class="meeting-initiator"
                      >{{ item.met_org }}发起的{{ item.met_type }}</span
                    >
                  </span>
                  <span
                    class="meeting-time-remainding-style"
                    :style="{ color: item.met_alert_color }"
                  >
                    <svg
                      :hidden="!item.before"
                      viewBox="0 0 24 24"
                      width="18px"
                      height="18px"
                      fill="currentColor"
                      aria-hidden="true"
                      focusable="false"
                    >
                      <path
                        d="M8.92 2.74h6.17a.72.72 0 000-1.44H8.92a.72.72 0 000 1.44zM16 15.77l-3.17-1.82-.62-5.59a1.06 1.06 0 00-.4-.72l-.51-.44v7.92L16.7 17l-.21-.61a1.05 1.05 0 00-.49-.62z"
                      ></path>
                      <path
                        d="M12.1 4.1a9.1 9.1 0 109.1 9.1 9.1 9.1 0 00-9.1-9.1zm0 16.8a7.7 7.7 0 117.7-7.7 7.71 7.71 0 01-7.7 7.7z"
                      ></path>
                      <path
                        d="M20.49 7.13a1 1 0 00-1.41-1.41l-.45.45a9.63 9.63 0 011.32 1.51z"
                      ></path>
                    </svg>
                    <svg
                      :hidden="item.before"
                      viewBox="0 0 24 24"
                      width="18px"
                      height="18px"
                      fill="currentColor"
                      aria-hidden="true"
                      focusable="false"
                    >
                      <path
                        d="M15.35 14.43h-.28c-.28 0-.62-.08-.62-.45v-.38a3.42 3.42 0 001.05-1.42 1.26 1.26 0 00.33-.19 1.28 1.28 0 00.47-.87l.16-1.52a1.28 1.28 0 00-.27-.93 1.3 1.3 0 00-.24-.24L16 7a.7.7 0 00-.17-.5l-.57-.66V4.7a.7.7 0 00-.7-.68h-1.73a5.23 5.23 0 00-4.39 2.1l-.31.44A.7.7 0 008 7l.1 1.55a1.3 1.3 0 00-.23.22 1.28 1.28 0 00-.27.93l.16 1.53a1.27 1.27 0 00.47.86 1.33 1.33 0 00.36.21 3.5 3.5 0 00.18.35 3.45 3.45 0 00.82.94V14c0 .37-.34.45-.62.45h-.29a4.75 4.75 0 00-4.41 3.73l-.14 1a.7.7 0 00.69.79H19.2a.7.7 0 00.69-.79l-.15-1.09a4.74 4.74 0 00-4.39-3.66zM5.63 18.6v-.2a3.32 3.32 0 013-2.56H9A1.86 1.86 0 0011 14v-.75a.7.7 0 00-.33-.6 2 2 0 01-.68-.7 2 2 0 01-.2-.47.7.7 0 00-.38-.48.69.69 0 00-.27 0L9 9.68A.7.7 0 009.52 9l-.11-1.83.17-.23a3.88 3.88 0 013.26-1.5h1v.7a.7.7 0 00.17.44l.55.63-.1 1.64a.69.69 0 00.17.52.67.67 0 00.37.21v1.28a.7.7 0 00-.67.53 2 2 0 01-.91 1.25.7.7 0 00-.33.6V14a1.86 1.86 0 002 1.85h.28a3.32 3.32 0 013 2.52v.25z"
                      ></path>
                      <path
                        d="M22.59 16.4a3.59 3.59 0 00-3.38-2.83H19c-.26 0-.26-.1-.26-.13v-.19a2.67 2.67 0 00.72-1 1.13 1.13 0 00.21-.14 1.12 1.12 0 00.41-.76l.11-1.1a1.13 1.13 0 00-.37-1l.06-.94a.7.7 0 00-.17-.5l-.37-.43v-.76a.7.7 0 00-.7-.68h-1.26a.7.7 0 000 1.4H18v.32a.7.7 0 00.17.44l.34.4-.07 1.11a.68.68 0 00.17.52.72.72 0 00.21.16l-.07.64a.7.7 0 00-.47.5 1.3 1.3 0 01-.57.79.7.7 0 00-.33.6v.54A1.55 1.55 0 0019 15h.2a2.2 2.2 0 012 1.65.7.7 0 00.2 1.35h.6a.7.7 0 00.69-.79zM2.78 16.63a2.21 2.21 0 012-1.65H5a1.55 1.55 0 001.66-1.53v-.55a.7.7 0 00-.33-.6 1.3 1.3 0 01-.57-.79.73.73 0 00-.47-.47l-.07-.68a.7.7 0 00.38-.66l-.1-1.11.34-.4A.7.7 0 006 7.76v-.33h.6A.7.7 0 006.6 6H5.34a.7.7 0 00-.7.68v.76l-.37.43a.7.7 0 00-.17.5l.06.94a1.12 1.12 0 00-.37 1l.11 1.11a1.12 1.12 0 00.41.76 1.16 1.16 0 00.21.14 2.66 2.66 0 00.72 1v.19s0 .13-.26.13h-.2a3.6 3.6 0 00-3.39 2.87l-.1.76A.7.7 0 002 18h.6a.7.7 0 00.2-1.37z"
                      ></path>
                    </svg>
                    <span class="meeting-time-remainding">{{
                      item.met_time_rem
                    }}</span>
                  </span>
                </div>

                <div
                  class="meeting-head-operation"
                  v-if="item.show_editor_and_cancel"
                >
                  <span class="operation-editor">
                    <a
                      class="meeting-editor"
                      type="button"
                      @click="
                        editor_meeting(
                          item.met_type,
                          item.met_number,
                          item.met_id
                        )
                      "
                    >
                      <svg
                        viewBox="0 0 24 24"
                        width="1em"
                        height="1em"
                        fill="currentColor"
                        aria-hidden="true"
                        focusable="false"
                      >
                        <path
                          d="M9.82 13.94l3-.28L20 6.53a1.56 1.56 0 000-2.2l-.55-.55a1.56 1.56 0 00-2.2 0l-7.15 7.14zm1.63-2.39l6.79-6.79a.16.16 0 01.22 0l.55.55a.16.16 0 010 .22l-6.78 6.78-.86.08z"
                        ></path>
                        <path
                          d="M19.6 11.94V19a.6.6 0 01-.6.6H5a.6.6 0 01-.6-.6V5a.6.6 0 01.6-.6h6.9V3H5a2 2 0 00-2 2v14a2 2 0 002 2h14a2 2 0 002-2v-7.06z"
                        ></path>
                      </svg>
                      编辑
                    </a>
                  </span>
                  <span class="operation-cancel">
                    <a
                      class="meeting-cancel"
                      type="button"
                      @click="
                        cancel_meeting(
                          item.met_type,
                          item.met_id,
                          item.met_number
                        )
                      "
                    >
                      <svg
                        viewBox="0 0 24 24"
                        width="1em"
                        height="1em"
                        fill="currentColor"
                        aria-hidden="true"
                        focusable="false"
                      >
                        <path
                          d="M16.64 7.9H4.83L7.74 5a.7.7 0 00-1-1L2 8.74l4.74 4.74a.7.7 0 101-1L4.55 9.3h12.09a4.6 4.6 0 010 9.2H8.4a.7.7 0 100 1.4h8.24a6 6 0 000-12z"
                        ></path>
                      </svg>
                      取消
                    </a>
                  </span>
                </div>
              </div>
              <div class="meeting-body">
                <div class="meeting-body-information" style="height: auto">
                  <div class="meeting-body-messages">
                    <div class="the-message">
                      <label class="message-label">预约时间</label>
                      <span class="message-information">{{
                        item.met_time
                      }}</span>
                    </div>
                    <div class="the-message" v-if="item.met_type=='预约会议'">
                      <label class="message-label" >开始时间</label>
                      <span class="message-information">{{
                        item.met_begin_time
                      }}</span>
                    </div>
                    <div class="the-message">
                      <label class="message-label">会议号</label>
                      <span class="message-information">{{
                        item.met_number
                      }}</span>
                    </div>
                    <div class="the-message" v-if="item.met_type=='预约会议室会议'">
                      <label class="message-label">会议室</label>
                      <span class="message-information">{{
                        item.met_room_name
                      }}</span>
                    </div>
                    <div class="the-message">
                      <label class="message-label">发起人</label>
                      <span class="message-information">{{
                        item.met_org
                      }}</span>
                    </div>
                    <div class="the-message">
                      <label class="message-label">参会人</label>
                      <span class="message-information">
                        <span class="message-information">{{
                          item.met_eglist
                        }}</span>
                        <a
                          type="button"
                          @click="showAttendee(item.met_list, item.met_number)"
                          class="message-information_show_more"
                          >查看更多</a
                        >
                      </span>
                    </div>
                  </div>
                  <div class="meeting-body-options">
                    <a-button
                      @click="start_meeting(item.met_number)"
                      :id="'start-meeting(' + item.met_id + ')'"
                      class="button-start"
                      >加入会议</a-button
                    >
                  </div>
                </div>
              </div>
            </div>
            <a-empty
              v-if="
                !List.reserved_meeting_list ||
                List.reserved_meeting_list.length == 0
              "
            />
          </div>
        </div>
      </a-tab-pane>
    </a-tabs>

    <a-modal
      v-model="attendee_visible"
      :title="attendee_meeting_title"
      @cancel="cancelShowAttendee"
      :footer="null"
    >
      <a-empty v-if="attendee_list.length == 0" />
      <a-table
        :columns="col"
        :data-source="attendee_list"
        :pagination="{ pageSize: 10 }"
        style="background: white; padding: 10px"
        v-else
      ></a-table>
    </a-modal>
  </a-card>
</template>
<script>
import { getMyMeetingList, cancelRoomReservation, cancelCommonReservation, } from '@/services/my.js'
import { mapGetters } from "vuex";
export default {
  data() {
    return {
      List: {
        holding_meeting_list: null,
        reserved_meeting_list: null
      },
      attendee_list: [],
      attendee_visible: false,
      attendee_meeting_title: '',
      col: [
        {
          title: '用户ID',
          dataIndex: 'user_id',
        },
        {
          title: '用户名',
          dataIndex: 'username',
        },
      ],
    }
  },
  computed: {
    ...mapGetters("account", ["username", "userId"]),
  },
  created() {//页面被创建时的操作
    this.getmsg()
  },
  mounted() {
    setInterval(() => {
      this.getmsg()
    }, 1000 * 5 * 60);
    setInterval(() => {
      if (this.List.holding_meeting_list) {
        this.List.holding_meeting_list.forEach((item) => {
          item.met_time_rem = this.Delta_T(item.the_start);
          item.met_alert_color = this.alert_15_min_rest(item.the_start);
          if (item.met_time_rem.startsWith("会议已开始，请加入会议")) {
            item.show_editor_and_cancel = false;
            item.before = false;
          }
        })
      }
      if (this.List.reserved_meeting_list) {
        this.List.reserved_meeting_list.forEach((item) => {
          item.met_time_rem = this.Delta_T(item.the_start);
          item.met_alert_color = this.alert_15_min_rest(item.the_start);
          if (item.met_time_rem.startsWith("会议已开始，请加入会议")) {
            item.show_editor_and_cancel = false;
            item.before = false;
          }
        })
      }
    }, 1000);
  },
  //在这里调用ajax请求方法
  methods: {
    getmsg() {
      getMyMeetingList().then(response => {
        // 请求成功回调函数
        var returnList = response.data.data
        this.put_into_UI(returnList);
      }).catch(error => {
        //请求失败回调函数
        console.log(error)
      });
    },
    put_into_UI(resultList) {
      this.List.holding_meeting_list = this.getitem(resultList.holding_meeting_list)
      this.List.reserved_meeting_list = this.getitem(resultList.reserved_meeting_list)
    },
    getitem(resultList) {
      var items = [];
      for (var obj in resultList) {
        var item = new Object;
        var is_host = true;//判定是否是主持人，通过判断用户id和host_id是否相同
        if (JSON.parse(resultList[obj].host_id) != this.userId) {
          is_host = false;
        }

        var early_than_start = true;//判定当前时间是否先于会议开始时间
        if (resultList[obj].scheduled_start_time == "") {
          early_than_start = false;
        } else {
          if (this.Delta_T(JSON.parse(resultList[obj].scheduled_start_time)).startsWith("会议已开始，请加入会议")) {
            early_than_start = false;
          }
        }


        var showEditorAndCancel = false;//联合前两个判断，选择是否展示编辑和取消
        if (is_host && early_than_start) {
          showEditorAndCancel = true;
        }


        var met_time_start = "", met_time_end = "";//输入异常处理

        if (resultList[obj].scheduled_start_time == 0) {
          met_time_start = "null";
        } else {
          met_time_start = this.getTime(JSON.parse(resultList[obj].scheduled_start_time));
        }

        if (resultList[obj].scheduled_end_time == 0) {
          met_time_end = "null";
        } else {
          met_time_end = this.getTime(JSON.parse(resultList[obj].scheduled_end_time));
        }

        var met_number = ''
        var met_id = ''
        var met_type = '';
        var met_room_name=''
        if (JSON.parse(JSON.stringify(resultList[obj].type)) == 1) {
          met_type = "立即会议";
          met_number = JSON.parse(JSON.stringify(resultList[obj].meeting_number))
          met_id = JSON.parse(JSON.stringify(resultList[obj].meeting_id))
        }
        else if (JSON.parse(JSON.stringify(resultList[obj].type)) == 2) {
          met_type = "预约会议";
          met_number = JSON.parse(JSON.stringify(resultList[obj].meeting_number))
          met_id = JSON.parse(JSON.stringify(resultList[obj].meeting_id))
        }
        else if (JSON.parse(JSON.stringify(resultList[obj].type)) == 3) {
          met_type = "预约会议室会议";
          met_number = JSON.parse(JSON.stringify(resultList[obj].room_meeting_id))
          met_id = JSON.parse(JSON.stringify(resultList[obj].room_id))
          met_room_name=JSON.parse(JSON.stringify(resultList[obj].room_name))
        }

        var met_sub = "null";
        if (resultList[obj].topic != "") {
          met_sub = JSON.parse(JSON.stringify(resultList[obj].topic));
        }

        var met_org = "null";
        if (resultList[obj].host_name != "") {
          met_org = JSON.parse(JSON.stringify(resultList[obj].hostname));
        }

        var met_time_rem = "null";
        if (resultList[obj].scheduled_start_time != "") {
          met_time_rem = this.Delta_T(JSON.parse(resultList[obj].scheduled_start_time));
        }

        var the_start = "null";
        if (resultList[obj].scheduled_start_time != "") {
          the_start = JSON.parse(resultList[obj].scheduled_start_time);
        }

        var met_list = [];
        var re_list = resultList[obj].participants
        for (var li in re_list) {
          met_list.push(re_list[li]);
        }

        var met_begin_time = "尚未开始";
        if (resultList[obj].start_time != 0) {
          met_begin_time = this.getTime(resultList[obj].start_time)
        }

        var met_time = "无";
        if (JSON.parse(JSON.stringify(resultList[obj].type)) != 1) {
          met_time = met_time_start + "    ————    " + met_time_end;
        }
        item = {
          is_host: is_host,//是否主持人
          the_start: the_start,//用于实时动态刷新
          met_alert_color: "black",//会议提示的颜色
          met_id: met_id,//会议id
          met_sub: met_sub,//会议主题
          met_type: met_type,//会议类型，1是临时会议，2是预约会议
          met_org: met_org,//会议主持人
          before: early_than_start,//判定当前时间是否是会议前
          show_editor_and_cancel: showEditorAndCancel,//判断编辑和取消按钮是否显示
          met_time_rem: met_time_rem,//离会议开始剩余时间
          met_time: met_time,//会议的起始时间
          met_number: met_number,//会议号
          met_eglist: met_org,//会议列表的举例显示
          met_list: met_list,//会议列表
          met_begin_time: met_begin_time,//会议开始时间
          met_room_name:met_room_name//会议室名称
        }
        items.push(item);
      }
      return items;
    },
    start_meeting(meetingNumber) {
      let routerurl = this.$router.resolve({
        path: '/meetingOn', query: { meetingNumber: meetingNumber }
      });
      window.open(routerurl.href, '_blank');
      this.getmsg()
    },
    start_room_meeting(roomMeetingId) {
      let routerurl = this.$router.resolve({
        path: '/roomMeetingOn', query: { roomMeetingId: roomMeetingId }
      });
      window.open(routerurl.href, '_blank');
      this.getmsg()
    },
    editor_meeting(meeting_type, meeting_number, meeting_id) {
      if (meeting_type == '预约会议') {
        this.$router.push({ name: '会议预约变更', params: { meetingNumber: meeting_number, meetingId: meeting_id } })
      } else if (meeting_type == '预约会议室会议') {
        this.$router.push({ name: '会议室预约变更', params: { roomMeetingId: meeting_number } })
      }
      this.getmsg()
    },
    cancel_meeting(meeting_type, meeting_id, meeting_number) {
      let self = this;
      this.meeting_id = meeting_id;
      this.$confirm({
        title: '确认提示',
        content: `是否确认取消会议？`,
        okType: 'danger',
        onOk() {
          if (meeting_type == '预约会议') {
            cancelCommonReservation(meeting_id,self.userId).then(res => {
              if (res.data.code == 200) {
                self.getmsg()
                self.$message.success("会议 " + meeting_number + " 取消成功! ")
              } else {
                self.getmsg()
                self.$message.error(res.data.errMsg)
              }
            }).catch(error => {
              console.log(error)
              self.$message.error("会议 " + meeting_number + " 取消失败！")
            })
          } else if (meeting_type == '预约会议室会议') {
            cancelRoomReservation(meeting_number).then(res => {
              if (res.data.code == 200) {
                self.getmsg()
                self.$message.success("会议 " + meeting_number + " 取消成功! ")
              } else {
                self.getmsg()
                self.$message.error(res.data.errMsg)
              }
            }).catch(error => {
              console.log(error)
              self.$message.error("会议 " + meeting_number + " 取消失败！")
            })
          }
        }
      })
    },
    cancelShowAttendee() {
      this.attendee_visible = false
    },
    showAttendee(participants, meeting_number) {
      this.attendee_meeting_title = "参会人（会议号：" + meeting_number + "）"
      this.attendee_list = participants
      this.attendee_visible = true
    },
    getTime: function (value) {//value为时间戳
      var date = new Date(value);
      var Y = date.getFullYear();
      var m = date.getMonth() + 1;
      var d = date.getDate();
      var H = date.getHours();
      var i = date.getMinutes();
      var s = date.getSeconds();
      if (m < 10) {
        m = '0' + m;
      }
      if (d < 10) {
        d = '0' + d;
      }
      if (H < 10) {
        H = '0' + H;
      }
      if (i < 10) {
        i = '0' + i;
      }
      if (s < 10) {
        s = '0' + s;
      }
      var t = Y + '/' + m + '/' + d + ' ' + H + ':' + i + ':' + s;
      return t;
    },

    //计算开始时间与当前时间之间的时间差 多少天时分秒
    Delta_T(startTime) {
      var timestamp = new Date().getTime();
      var date1 = "";
      if (timestamp > startTime) {
        return "会议已开始，请加入会议"
      } else {
        date1 = timestamp; //当前时间
      }
      var date2 = startTime; //开始时间
      var date3 = date2 - date1; //时间差的毫秒数

      //计算出相差天数
      var days = Math.floor(date3 / (24 * 3600 * 1000));
      //计算出小时数
      var leave1 = date3 % (24 * 3600 * 1000); //计算天数后剩余的毫秒数
      var hours = Math.floor(leave1 / (3600 * 1000));
      //计算相差分钟数
      var leave2 = leave1 % (3600 * 1000); //计算小时数后剩余的毫秒数
      var minutes = Math.floor(leave2 / (60 * 1000));
      //计算相差秒数
      var leave3 = leave2 % (60 * 1000); //计算分钟数后剩余的毫秒数
      var seconds = Math.round(leave3 / 1000);
      if (days == 0) {
        var rmsg = "距离会议预约开始时间还有";
        if (hours < 10) {
          rmsg += "0" + hours;
        } else {
          rmsg += hours;
        }
        if (minutes < 10) {
          rmsg += ":0" + minutes;
        } else {
          rmsg += ":" + minutes;
        }
        if (seconds < 10) {
          rmsg += ":0" + seconds;
        } else {
          rmsg += ":" + seconds;
        }
        return rmsg;
      } else {
        return "距离会议开始还有" + days + "天";
      }
    },
    alert_15_min_rest: function (startTime) {
      var timestamp = new Date().getTime();
      var date1 = "";
      if (timestamp > startTime) {
        return "red";
      } else {
        date1 = timestamp; //当前时间
      }
      var date2 = startTime; //开始时间
      var date3 = date2 - date1; //时间差的毫秒数

      //计算出相差天数
      var seconds = Math.floor(date3 / 1000);
      if (seconds < 900) {
        return "red";
      } else {
        return "black";
      }
    }

  }
}
</script>
