import axios from "axios";

// 서버로 부터 받아오는 데이터 1건에 대한 타입
export interface ContactItemResponse {
  id: number;
  name: string;
  phone: string;
  email: string;
  memo: string;
  createdTime: number;
}

export interface ContactItemRequest {
  name: string;
  phone: string;
  email: string;
  memo: string;
}

// 서버 데이터 연동 API
const contactApi = {
  // GET 요청 URL
  fetch: () =>
    axios.get<ContactItemResponse[]>(
      `${process.env.REACT_APP_API_BASE}/contacts`
    ),

  // POST 요청 URL
  add: (contactItem: ContactItemRequest) =>
    axios.post<ContactItemResponse>(
      `${process.env.REACT_APP_API_BASE}/contacts`,
      contactItem
    ),

  // DELETE 요청 URL
  remove: (id: number) =>
    axios.delete<boolean>(`${process.env.REACT_APP_API_BASE}/contacts/${id}`),

  // PUT 요청 URL
  modify: (id: number, contactItem: ContactItemRequest) =>
    axios.put<ContactItemResponse>(
      `${process.env.REACT_APP_API_BASE}/contacts/${id}`,
      contactItem
    ),
};

export default contactApi;
