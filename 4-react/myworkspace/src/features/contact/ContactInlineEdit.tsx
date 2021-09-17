// import userEvent from "@testing-library/user-event";
import produce from "immer";
// import { iteratorSymbol } from "immer/dist/internal";
import { useState, useRef, useEffect } from "react";

import api from "./contactApi";

// 1건에 대한 타입
interface ContactState {
  id: number;
  name?: string | undefined;
  phone?: string;
  email?: string;
  isEdit?: boolean;
}

const Contact = () => {
  // 여러건에 대한 state
  const [contactList, setContactList] = useState<ContactState[]>([]);

  // 데이터 받아옴 완료여부를 표시 state
  const [isLoading, setLoading] = useState<Boolean>(true);

  const formRef = useRef<HTMLFormElement>(null);
  const inputNameRef = useRef<HTMLInputElement>(null);
  const inputPhoneRef = useRef<HTMLInputElement>(null);
  const inputEmailRef = useRef<HTMLInputElement>(null);
  const tbodyRef = useRef<HTMLTableSectionElement>(null);

  const fetchData = async () => {
    // 백엔드에서 데이터 받아옴
    const res = await api.fetch();

    // axios에서 응답받은 데이터는 data속성에 들어가 있음
    // 서버로부터 받은 데이터를 state 객체로 변환함
    const contacts = res.data.map((item) => ({
      id: item.id,
      name: item.name,
      phone: item.phone,
      email: item.email,
    })) as ContactState[];

    // 로딩중인 state 업데이트
    setLoading(false);

    // contact state 업데이트
    setContactList(contacts);
    console.log("-- await axios.get completed --");
  };

  // useEffect
  useEffect(() => {
    // 백엔드에서 데이터를 받아올 것임
    // ES8 style로 async-await 기법을 이용해서 데이터를 조회해옴
    fetchData();
  }, []);

  // 추가
  const add = async () => {
    // ------------------------ 백엔드 연동 부분 ------------------------
    const result = await api.add({
      name: inputNameRef.current?.value,
      phone: inputPhoneRef.current?.value,
      email: inputEmailRef.current?.value,
    });
    console.log(result);

    // ------------------------ state 변경부분 ------------------------
    const contact: ContactState = {
      id: result.data.id,
      name: result.data.name,
      phone: result.data.phone,
      email: result.data.email,
    };

    // const contact: ContactState = {
    //   id: contactList.length > 0 ? contactList[0].id + 1 : 1,
    //   name: inputNameRef.current?.value,
    //   phone: inputPhoneRef.current?.value,
    //   email: inputEmailRef.current?.value,
    // };

    setContactList(
      produce((state) => {
        state.unshift(contact);
      })
    );

    formRef.current?.reset();
  };

  // 삭제
  const del = async (id: number, index: number) => {
    console.log(id);

    // ------------------------ 백엔드 연동 부분 ------------------------
    const result = await api.remove(id);
    console.log(result.status);

    // immer로 state 배열 직접 조작(index로 삭제)
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

  // 저장
  const save = async (id: number, index: number) => {
    // tbody밑에있는 tr행에 모든 입력박스 선택
    const input = tbodyRef.current
      ?.querySelectorAll("tr")
      [index].querySelectorAll("input");

    // ------------------------ 백엔드 연동 부분 ------------------------
    if (!input) return;
    const result = await api.modify(id, {
      name: input.item(0).value,
      phone: input.item(1).value,
      email: input.item(2).value,
    });
    console.log(result.status);

    // ------------------------ state 변경부분 ------------------------
    setContactList(
      produce((state) => {
        const item = state.find((item) => item.id === id);
        if (item) {
          item.name = result.data.name;
          item.phone = result.data.phone;
          item.email = result.data.email;
          item.isEdit = false;
        }
      })
    );

    // setContactList(
    //   produce((state) => {
    //     const item = state.find((item) => item.id === id);
    //     if (item) {
    //       item.name = input?.item(0).value;
    //       item.phone = input?.item(1).value;
    //       item.email = input?.item(2).value;
    //       item.isEdit = false;
    //     }
    //   })
    // );
  };

  return (
    <div style={{ width: "60vw" }} className="mx-auto">
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
          ref={inputPhoneRef}
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
          <tr className="text-center">
            <th scope="col">#</th>
            <th scope="col">이름</th>
            <th scope="col">전화번호</th>
            <th scope="col">이메일</th>
            <th scope="col">작업</th>
          </tr>
        </thead>
        <tbody ref={tbodyRef}>
          {/* 로딩중 처리 표시 */}
          {isLoading && (
            <tr>
              <td className="text-center" colSpan={5}>
                <div className="spinner-border text-primary " role="status">
                  <span className="visually-hidden">Loading...</span>
                </div>
              </td>
            </tr>
          )}
          {/* 빈 값 데이터 표시 */}
          {!isLoading && contactList.length === 0 && (
            <tr className="text-center">
              <td colSpan={5}>데이터가 없습니다.</td>
            </tr>
          )}
          {contactList.map((item, index) => (
            <tr className="text-center" key={item.id}>
              {/* 보기모드일때 */}
              {!item.isEdit && <td>{item.id}</td>}
              {!item.isEdit && <td>{item.name}</td>}
              {!item.isEdit && <td>{item.phone}</td>}
              {!item.isEdit && <td>{item.email}</td>}
              {/* 수정모드일때 */}
              {item.isEdit && <td>{item.id}</td>}
              {item.isEdit && (
                <td>
                  <input type="text" defaultValue={item.name} />
                </td>
              )}
              {item.isEdit && (
                <td>
                  <input type="text" defaultValue={item.phone} />
                </td>
              )}
              {item.isEdit && (
                <td>
                  <input type="text" defaultValue={item.email} />
                </td>
              )}

              {/* 보기모드일때 */}
              {!item.isEdit && (
                <td>
                  <button
                    className="btn btn-outline-secondary btn-sm text-nowrap me-1"
                    onClick={() => {
                      edit(item.id, true);
                    }}
                  >
                    수정
                  </button>

                  <button
                    className="btn btn-outline-secondary btn-sm text-nowrap"
                    onClick={() => {
                      del(item.id, index);
                    }}
                  >
                    삭제
                  </button>
                </td>
              )}

              {/* 수정모드일때 */}
              {item.isEdit && (
                <td>
                  <button
                    className="btn btn-outline-secondary btn-sm text-nowrap me-1"
                    onClick={() => {
                      save(item.id, index);
                    }}
                  >
                    저장
                  </button>

                  <button
                    className="btn btn-outline-secondary btn-sm text-nowrap"
                    onClick={() => {
                      edit(item.id, false);
                    }}
                  >
                    취소
                  </button>
                </td>
              )}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};
export default Contact;
