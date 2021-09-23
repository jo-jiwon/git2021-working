import axios from "axios";

// 서버로 부터 받아오는 데이터 1건에 대한 타입
interface ContactResponse {
  id: number;
  name?: string | undefined;
  phone?: string;
  email?: string;
}

interface ContactRequest {
  name?: string | undefined;
  phone?: string;
  email?: string;
}

const contactApi = {
  fetch: () =>
    // axios.get<응답데이터의 타입>(응답URL)
    // Process.env.변수명
    axios.get<ContactResponse[]>(`${process.env.REACT_APP_API_BASE}/contacts`),

  add: (contactItem: ContactRequest) =>
    axios.post<ContactResponse>(
      `${process.env.REACT_APP_API_BASE}/contacts`,
      contactItem
    ),

  remove: (id: number) =>
    axios.delete<boolean>(`${process.env.REACT_APP_API_BASE}/contacts/${id}`),

  modify: (id: number, contactItem: ContactRequest) =>
    axios.put<ContactResponse>(
      `${process.env.REACT_APP_API_BASE}/contacts/${id}`,
      contactItem
    ),
};

export default contactApi;
