CREATE or REPLACE VIEW v_current_case_stage
AS
SELECT a.case_id, MAX(a.case_stage) case_stage
FROM tcase_process a
WHERE a.case_stage_status>'0'
AND a.case_stage!='110'
AND a.case_stage!='120'
GROUP BY a.case_id;

CREATE or REPLACE VIEW v_current_case_process
AS
SELECT b.* FROM v_current_case_stage a
LEFT JOIN tcase_process b on b.case_id=a.case_id AND b.case_stage=a.case_stage
UNION
SELECT c.*
FROM tcase_process c
WHERE c.case_stage_status>'0'
AND c.case_stage in ('110','120','210');