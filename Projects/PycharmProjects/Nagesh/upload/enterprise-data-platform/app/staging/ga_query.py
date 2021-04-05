ga_session_155528388 = """WITH transactionIds AS (
  SELECT DISTINCT
    CONCAT(fullVisitorId, ".", CAST(visitId AS STRING)) AS sessionId,
    h.transaction.transactionId
  FROM `unified-ruler-180608.155528388.ga_sessions_20190710` AS s, UNNEST(s.hits) AS h
  WHERE h.transaction.transactionId IS NOT NULL
    )

    SELECT
  CONCAT(fullVisitorId, ".", CAST(visitId AS STRING)) AS SessionId,
  date as Date,
  TIMESTAMP_SECONDS(visitStartTime) AS visitStartTime,
  totals.visits as Sessions,
  totals.bounces as Bounces,
  (SELECT MAX(
    CASE WHEN eventInfo.eventCategory = "EEC" AND REGEXP_CONTAINS(eventInfo.eventAction, "(Add To Cart|Checkout Steps)")
    THEN 1 ELSE 0 END) FROM UNNEST(hits) ) AS Goal1,
  totals.transactions as Transactions,
  (totals.totalTransactionRevenue)/1000000 as Revenue,
  clientId as WebsiteVisitorsID,
  totals.newVisits as NewWebsiteVisitors,
  (SELECT value FROM s.customDimensions WHERE index=6) AS UserAccount,
  transactionId AS TransactionID,
  channelGrouping as ChannelGrouping,
  (SELECT CASE WHEN type = "APPVIEW" THEN
     appInfo.screenName
    ELSE
      page.pagePath
    END

   FROM UNNEST(hits) WHERE isEntrance ) AS landingPage,
  totals.pageviews AS PageView,
  totals.timeOnSite as SessionDuration,
  device.deviceCategory as DeviceCategory,
  device.mobileDeviceInfo as mobiledevice,
  trafficSource.source as TrafficSource,
  (SELECT sourcePropertyInfo.sourcePropertyTrackingId FROM UNNEST(hits) WHERE hitNumber = 1) AS TrackingID,
  (SELECT dataSource FROM UNNEST(hits) WHERE hitNumber = 1) AS SourceSystemID
FROM
  `unified-ruler-180608.155528388.ga_sessions_*` s
  LEFT JOIN transactionIds ON transactionIds.sessionId = CONCAT(fullVisitorId, ".", CAST(visitId AS STRING))
    WHERE (_TABLE_SUFFIX BETWEEN \'{0}\' AND \'{1}\')
      AND (TIMESTAMP_SECONDS(visitStartTime) > \'{2}\')
    ORDER BY visitStartTime ASC"""

blank_query = "select  * from dummy"

query_dict = {
    "ga_session_155528388": ga_session_155528388,
    "another_query_id_from_config": blank_query,
}
