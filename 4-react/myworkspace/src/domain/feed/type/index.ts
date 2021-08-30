import { ProfileState } from "../../profile/profileSlice";

interface FeedState {
  id: number;
  memo: string | undefined;
  username: string | undefined;
  image: string | undefined;
  dataUrl?: string | undefined;
  createTime: number;
  fileType?: string | undefined; // 파일타입 img, video
}
export type { FeedState };
