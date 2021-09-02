import { useRef } from "react";

import { FeedState } from "./type";
// {함수속성}
// 함수속성의 타입: (매개변수) => 리턴타입

// 함수(ex. 부모state제어)를 부모컴포넌트로 부터 매개변수를 받음
// 자식컴포넌트에서 이벤트가 발생하면 부모로부터 받은 함수를 실행

interface ModalProp {
  item: FeedState;
  onClose: () => void;
  onSave: (editItem: FeedState) => void;
}

const FeedEditModal = ({ item, onClose, onSave }: ModalProp) => {
  const textRef = useRef<HTMLTextAreaElement>(null);
  const fileRef = useRef<HTMLInputElement>(null);

  const save = () => {
    if (fileRef.current?.files?.length) {
      const file = fileRef.current?.files[0];
      const reader = new FileReader();
      reader.onload = () => {
        // 부모컴포넌트에 event-up할 새로운 객체들
        // 메모, 업로드시간, dataurl, 이미지/동영상변경을 위한 filetype
        const feed: FeedState = {
          id: item.id,
          memo: textRef.current?.value,
          username: item.username,
          image: item.image,
          createTime: new Date().getTime(),
          dataUrl: reader.result?.toString(),
          fileType: reader.result?.toString(),
        };
        onSave(feed);
      };
      reader.readAsDataURL(file);
    }
  };
  return (
    <div
      className="modal d-block"
      style={{ backgroundColor: "rgba(0, 0, 0, 0.5)" }}
      onClick={() => {
        onClose();
      }}
    >
      <div className="modal-dialog">
        <div className="modal-content" onClick={(e) => e.stopPropagation()}>
          <div className="modal-header">
            <h5 className="modal-title">Edit Feed</h5>
            <button
              type="button"
              className="btn-close"
              aria-label="Close"
              onClick={() => {
                onClose();
              }}
            ></button>
          </div>
          <div className="modal-body">
            {/* 텍스트와 이미지 수정가능하게 */}
            <form>
              <div className="mb-3">
                {item.fileType &&
                  (item.fileType?.includes("image") ? (
                    <img
                      src={item.dataUrl}
                      className="card-img-top"
                      alt={item.memo}
                    />
                  ) : (
                    <video className="card-img-top" controls>
                      <source src={item.dataUrl} type="video/mp4"></source>
                    </video>
                  ))}
              </div>
              <div className="mb-3 d-flex">
                <input
                  type="file"
                  accept="image/png, image/jpeg, video/mp4"
                  className="form-control"
                  ref={fileRef}
                />
              </div>
              <div className="mb-3 d-flex">
                <textarea
                  defaultValue={item.memo}
                  className="form-control"
                  ref={textRef}
                />
              </div>
            </form>
          </div>
          <div className="modal-footer">
            <button
              type="button"
              className="btn btn-secondary"
              onClick={() => {
                onClose();
              }}
            >
              닫기
            </button>
            <button
              type="button"
              className="btn btn-primary"
              onClick={() => {
                save();
              }}
            >
              저장
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default FeedEditModal;
