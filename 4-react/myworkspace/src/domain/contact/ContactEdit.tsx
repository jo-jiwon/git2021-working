import { useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useHistory, useParams } from "react-router-dom";
import { AppDispatch, RootState } from "../../store";
import { editContact } from "./contactSlice";
const ContactEdit = () => {
  // input ref객체
  const inputNameRef = useRef<HTMLInputElement>(null);
  const inputPhoneRef = useRef<HTMLInputElement>(null);
  const inputEmailRef = useRef<HTMLInputElement>(null);
  const descTextAreaRef = useRef<HTMLTextAreaElement>(null);

  const history = useHistory();
  const diepatch = useDispatch<AppDispatch>();

  // id 데이터 가져옴
  const { id } = useParams<{ id: string }>();
  const contactItem = useSelector((state: RootState) =>
    state.contact.data.find((item) => item.id === +id)
  );

  // 저장 변경할 객체 생성
  const handSaveClick = () => {
    if (contactItem) {
      // 기존 데이터 카피
      const item = { ...contactItem };
      // 변경할 속성 대입
      item.name = inputNameRef.current ? inputNameRef.current?.value : "";
      item.phone = inputPhoneRef.current ? inputPhoneRef.current?.value : "";
      item.email = inputEmailRef.current ? inputEmailRef.current?.value : "";
      item.description = descTextAreaRef.current
        ? descTextAreaRef.current?.value
        : "";

      // 저장 누르면 입력값 잘 나오는지 확인
      console.log(item.name);
      console.log(item.phone);
      console.log(item.email);
      console.log(item.description);

      diepatch(editContact(item));
      history.push("/contacts");
    }
  };

  return (
    <div className="mx-auto" style={{ width: "40vw" }}>
      <h2 className="text-center">Contact Edit</h2>

      <table className="table">
        <tbody>
          <tr>
            <th>이름</th>
            <td>
              <input
                className="form-control"
                type="text"
                ref={inputNameRef}
                defaultValue={contactItem?.name}
              />
            </td>
          </tr>
          <tr>
            <th>전화번호</th>
            <td>
              <input
                className="form-control"
                type="text"
                ref={inputPhoneRef}
                defaultValue={contactItem?.phone}
              />
            </td>
          </tr>
          <tr>
            <th>이메일</th>
            <td>
              <input
                className="form-control"
                type="text"
                ref={inputEmailRef}
                defaultValue={contactItem?.email}
              />
            </td>
          </tr>
          <tr>
            <th>메모</th>
            <td>
              <textarea
                className="form-control"
                style={{ height: "30vh" }}
                ref={descTextAreaRef}
                defaultValue={contactItem?.description}
              ></textarea>
            </td>
          </tr>
        </tbody>
      </table>

      <div>
        <button
          className="btn btn-secondary text-nowrap float-start"
          onClick={() => {
            history.push("/contacts");
          }}
        >
          <i className="bi bi-list-ul me-1" />
          목록
        </button>
        <button
          className="btn btn-primary text-nowrap float-end"
          onClick={() => {
            handSaveClick();
          }}
        >
          <i className="bi bi-check me-1" />
          저장
        </button>
      </div>
    </div>
  );
};

export default ContactEdit;
