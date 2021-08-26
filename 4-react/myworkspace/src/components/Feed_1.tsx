import React, { useRef, useState } from "react";
import { Redirect } from "react-router-dom";
import { classicNameResolver, isTemplateExpression } from "typescript";

import produce from "immer";

// 1건에 대한 타입
interface FeedState {
  id: number;
  memo?: string | undefined;
  dataUrl?: string | undefined;
  fileType?: string | undefined;
  createTime: number;
  modifyTime?: number;
  isEdit?: boolean; // 수정모드인지 여부
}

// 날짜/시간 모듈 생성
const getTimeString = (unixtime: number) => {
  // Locale: timezone, currency 등
  // js에서는 브라우저의 정보를 이용함
  const dateTime = new Date(unixtime);
  return `${dateTime.toLocaleDateString()} ${dateTime.toLocaleTimeString()}`;
};

const FeedEditModal = () => {
  return (
    <div className="modal d-block">
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title">Edit Feed</h5>
            <button
              type="button"
              className="btn-close"
              aria-label="Close"
            ></button>
          </div>
          <div className="modal-body">
            <input type="text" className="w-100" />
          </div>
          <div className="modal-footer">
            <button type="button" className="btn btn-secondary">
              닫기
            </button>
            <button type="button" className="btn btn-primary">
              저장
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

const Feed_1 = () => {
  // feed state
  const [feedList, setFeedList] = useState<FeedState[]>([]);

  const [isEdit, setIsEdit] = useState(false);

  // 빈 값 여부 state
  const formRef = useRef<HTMLFormElement>(null);
  const textRef = useRef<HTMLTextAreaElement>(null);
  const fileRef = useRef<HTMLInputElement>(null);

  // Enter이벤트 생성
  const add = (e: React.KeyboardEvent<HTMLInputElement> | null) => {
    // 이벤트 객체가 있을 때는 입력박스에서 엔터 입력
    if (e) {
      if (e.code !== "Enter") return;
    }

    if (fileRef.current?.files?.length) {
      const file = fileRef.current?.files[0];
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        post(reader.result?.toString(), file.type);
      };
    } else {
      post(undefined, undefined);
    }
  };

  // 컨텐츠
  const post = (dataUrl: string | undefined, fileType: string | undefined) => {
    const feed: FeedState = {
      id: feedList.length > 0 ? feedList[0].id + 1 : 1,
      memo: textRef.current?.value,
      dataUrl: dataUrl,
      fileType: fileType,
      createTime: new Date().getTime(),
    };

    // 새로운 피드 앞으로 생성
    // setFeedList([feed, ...feedList]);

    setFeedList(
      produce((state) => {
        state.unshift(feed);
      })
    );

    // 입력값 초기화
    formRef.current?.reset();
  };

  // 삭제
  const del = (id: number) => {
    // setFeedList(feedList.filter((item) => item.id !== id));

    setFeedList(
      produce((state) => {
        const item = state.find((item) => item.id === id);
        if (item) {
          state.splice(state.indexOf(item), 1);
        }
      })
    );
  };

  const edit = () => {
    setIsEdit(true);
  };
  return (
    <>
      <h2 className="text-center my-5">Feed</h2>
      {isEdit && <FeedEditModal />}
      <form
        ref={formRef}
        onSubmit={(e) => {
          e.preventDefault();
        }}
      >
        <textarea
          className="form-control mb-1"
          placeholder="Leave a post here"
          style={{ boxSizing: "border-box" }}
          ref={textRef}
        ></textarea>
        <div className="d-flex">
          <input
            type="file"
            className="form-control me-1"
            accept="image/png, image/jpeg, video/mp4"
            ref={fileRef}
          />
          <button
            className="btn btn-primary text-nowrap"
            type="button"
            onClick={() => {
              add(null);
            }}
          >
            입력
          </button>
        </div>
      </form>
      <div className="mt-3">
        {/* 데이터와 UI요소 바인딩 */}
        {feedList.map((item) => (
          <div className="card mt-1" key={item.id}>
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
            <div className="card-body">
              <p className="card-text">{item.memo}</p>
              <div className="d-flex">
                <div className="w-100">
                  <span>
                    {getTimeString(
                      item.modifyTime ? item.modifyTime : item.createTime
                    )}
                  </span>
                </div>
                <a
                  href="#!"
                  onClick={() => {
                    edit();
                  }}
                  className="link-secondary text-nowrap me-2"
                >
                  수정
                </a>
                <a
                  href="#!"
                  onClick={(e) => {
                    e.preventDefault();
                    del(item.id);
                  }}
                  className="link-secondary text-nowrap"
                >
                  삭제
                </a>
              </div>
            </div>
          </div>
        ))}
      </div>
    </>
  );
};

export default Feed_1;
