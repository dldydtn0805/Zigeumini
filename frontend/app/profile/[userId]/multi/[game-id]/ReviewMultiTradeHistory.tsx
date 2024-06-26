import userStore from "@/public/src/stores/user/userStore";
import MultiReviewStore from "@/public/src/stores/profile/MultiReviewStore";

export default function MultiTradeHistory() {
  const { memberId } = userStore();
  const { multiLogMemberDtoList } = MultiReviewStore();
  const userInfo :any = multiLogMemberDtoList.find((x :any) => x.memberId == memberId);
  
  return (
    <div className="row-span-3 grid grid-rows-12 bg-small-10 m-1 rounded-md">
      <div className="row-span-2 flex items-center justify-center">
        <div className="text-textColor-2">{userInfo?.nickname}님의 매매내역</div>
      </div>
      <table className="row-span-10 grid grid-row-10 table-fixed rounded-md overflow-y-auto m-1">
        <thead className="row-span-2 grid grid-cols-6 items-center">
          <tr className="col-span-6 grid grid-cols-4 items-center">
            <th className="col-span-2 text-center">유형</th>
            <th className="col-span-2 text-center">가격(수량)</th>
          </tr>
        </thead>
        <tbody className="row-span-10 grid-rows-12 overflow-y-auto block" style={{ height: "calc(25vh)"}}>           
          {
            userInfo && userInfo.multiLogTradeDtoList.length > 0 ? (
              
              userInfo.multiLogTradeDtoList?.map((item :any, index :number) => (
                  <tr key={index} className={`row-span-1 grid grid-cols-4 text-center bg-white ${item.tradeType=="BUY" ? "bg-red-500 text-white" : (item.tradeType=="SELL" ? "bg-blue-500 text-white" : "bg-white text-yellow-500" )}rounded-lg m-1`}>
                    <td className="col-span-2">{item.tradeType}</td>
                    <td className="col-span-2">{item.price}{(item.amount)}</td>                      
                  </tr>
              ))
            ) : (
              <div className="row-span-4 flex justify-center mt-4">매매 내역이 없습니다.</div>
            )
          }
        </tbody>
      </table>
    </div>
  );
}
