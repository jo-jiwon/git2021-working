// import userEvent from "@testing-library/user-event";
import produce from "immer";
// import { iteratorSymbol } from "immer/dist/internal";
import { useState, useRef } from "react";

interface ContactState {
  id: number;
  name?: string | undefined;
  phone?: string;
  email?: string;
  isEdit?: boolean;
}

const Contact = () => {
  const [contactList, setContactList] = useState<ContactState[]>([]);

  const formRef = useRef<HTMLFormElement>(null);
  const inputNameRef = useRef<HTMLInputElement>(null);
  const inputNumRef = useRef<HTMLInputElement>(null);
  const inputEmailRef = useRef<HTMLInputElement>(null);

  // 추가
  const add = () => {
    const contact: ContactState = {
      id: contactList.length > 0 ? contactList[0].id + 1 : 1,
      name: inputNameRef.current?.value,
      phone: inputNumRef.current?.value,
      email: inputEmailRef.current?.value,
    };
    setContactList(
      produce((state) => {
        state.unshift(contact);
      })
    );

    formRef.current?.reset();
  };

  // 삭제
  const del = (id: number, index: number) => {
    setContactList(
      produce((state) => {
        state.splice(index, 1);
      })
    );
  };

  // 수정
  const edit = (id: number, mod: boolean) => {
    setContactList(
      produce((state) => {
        const item = state.find((item) => item.id === id);
        if (item) {
          item.isEdit = mod;
        }
      })
    );
  };
  return (
    <>
      <h2 className="text-center my-5">연락처 관리</h2>
      <form className="d-flex" ref={formRef}>
        <input
          type="text"
          className="form-control me-1"
          placeholder="이름"
          ref={inputNameRef}
        />
        <input
          type="text"
          className="form-control me-1"
          placeholder="전화번호"
          ref={inputNumRef}
        />
        <input
          type="text"
          className="form-control me-2"
          placeholder="이메일"
          ref={inputEmailRef}
        />
        <button
          type="button"
          className="btn btn-outline-primary text-nowrap"
          onClick={() => {
            add();
          }}
        >
          추가
        </button>
      </form>

      <table className="table table-striped">
        <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">이름</th>
            <th scope="col">전화번호</th>
            <th scope="col">이메일</th>
            <th scope="col">작업</th>
          </tr>
        </thead>
        <tbody>
          {contactList.map((item, index) => (
            <tr key={item.id}>
              {!item.isEdit && (
                <tr>
                  <td>{item.name}</td>
                  <td>{item.phone}</td>
                  <td>{item.email}</td>
                </tr>
              )}
              {item.isEdit && (
                <input type="text" className="w-100" defaultValue={item.name} />
              )}
              {item.isEdit && (
                <input
                  type="text"
                  className="w-100"
                  defaultValue={item.phone}
                />
              )}
              {item.isEdit && (
                <input
                  type="text"
                  className="w-100"
                  defaultValue={item.email}
                />
              )}
              <td>
                <button
                  className="btn btn-outline-secondary btn-sm text-nowrap"
                  onClick={() => {
                    edit(item.id, true);
                  }}
                >
                  수정
                </button>
              </td>
              <td>
                <button
                  className="btn btn-outline-secondary btn-sm text-nowrap"
                  onClick={() => {
                    del(item.id, index);
                  }}
                >
                  삭제
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </>
  );
};
export default Contact;
