import { useSelector } from "react-redux";
import { useHistory } from "react-router-dom";
import { RootState } from "../../store";

// 날짜/시간
const getTimeString = (unixtime: number) => {
  const dateTime = new Date(unixtime);
  return `${dateTime.toLocaleDateString()} ${dateTime.toLocaleTimeString()}`;
};

const Contact = () => {
  // contact state값 가져오기
  const contact = useSelector((state: RootState) => state.contact);
  // 화면 이동
  const history = useHistory();

  return (
    <div className="mx-auto" style={{ width: "40vw" }}>
      <h2 className="text-center my-5">Contact</h2>
      <div className="d-flex justify-content-end mb-2">
        <button
          type="button"
          className="btn btn-primary text-nowrap"
          onClick={() => {
            history.push("/contacts/create");
          }}
        >
          <i className="bi bi-plus me-1" />
          추가
        </button>
      </div>
      <table className="table table-hover">
        <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">이름</th>
            <th scope="col">전화번호</th>
            <th scope="col">이메일</th>
            <th scope="col">작성일시</th>
          </tr>
        </thead>
        <tbody>
          {contact.data.map((item, index) => (
            <tr
              key={`contact-item-${index}`}
              style={{ cursor: "pointer" }}
              onClick={() => {
                history.push(`/contacts/detail/${item.id}`);
              }}
            >
              <td>{item.id}</td>
              <td>{item.name}</td>
              <td>{item.phone}</td>
              <td>{item.email}</td>
              <td>{getTimeString(item.createTime)}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Contact;
