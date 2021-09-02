import { createSlice, PayloadAction } from "@reduxjs/toolkit";

// 1열로 구성된 목록조회(순번, 이름, 전화번호, 이메일, 작성일시, 메모)

// 데이터구조
export interface ContactItem {
  id: number;
  name: string;
  phone: string;
  email: string;
  createTime: number;
  description: string;
}

// 이건 백엔드 연동때문에 설계하셨다함 NEXT
interface ContactState {
  data: ContactItem[];
  isFetched: boolean;
}

// contact state 목록
const initialState: ContactState = {
  data: [
    {
      id: 3,
      name: "JiWon Jo",
      phone: "010-9105-3768",
      email: "jjw3768@naver.com",
      createTime: new Date().getTime(),
      description: "연락처 조회 만들어보장",
    },
    {
      id: 2,
      name: "JiWon Jo",
      phone: "010-9105-3768",
      email: "jjw3768@naver.com",
      createTime: new Date().getTime(),
      description: "연락처 조회 만들어보장",
    },
    {
      id: 1,
      name: "JiWon Jo",
      phone: "010-9105-3768",
      email: "jjw3768@naver.com",
      createTime: new Date().getTime(),
      description: "연락처 조회 만들어보장",
    },
  ],
  isFetched: false,
};

const contactSlice = createSlice({
  name: "contact",
  initialState,
  reducers: {
    addContact: (state, action: PayloadAction<ContactItem>) => {
      const contact = action.payload;
      state.data.unshift(contact);
    },
  },
});

export const { addContact } = contactSlice.actions;

export default contactSlice.reducer;
