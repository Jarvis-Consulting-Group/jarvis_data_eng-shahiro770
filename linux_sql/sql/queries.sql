-- INSERT INTO host_info (id, hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, l2_cache, "timestamp", total_mem)
-- VALUES(1, 'jrvs-remote-desktop-centos7-6.us-central1-a.c.spry-framework-236416.internal', 1, 'x86_64', 'Intel(R) Xeon(R) CPU @ 2.30GHz', 2300, 256, '2019-05-29 17:49:53.000', 601324);
--
-- INSERT INTO host_info (id, hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, l2_cache, "timestamp", total_mem)
-- VALUES(1, 'noe1', 1, 'x86_64', 'Intel(R) Xeon(R) CPU @ 2.30GHz', 2300, 256, '2019-05-29 17:49:53.000', 601324);
--
-- INSERT INTO host_info (id, hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, l2_cache, "timestamp", total_mem)
-- VALUES(6, 'def6', 2, 'x86_64','Intel(R) Xeon(R) CPU @ 2.30GHz', 2300, 256, '2019-05-29 17:49:53.000', 10000);


INSERT INTO host_usage ("timestamp", host_id, memory_free, cpu_idle, cpu_kernel, disk_io,
                        disk_available)
VALUES ('2019-05-29 15:00:00.000', 1, 300000, 90, 4, 2, 3);
INSERT INTO host_usage ("timestamp", host_id, memory_free, cpu_idle, cpu_kernel, disk_io,
                        disk_available)
VALUES ('2019-05-29 15:01:00.000', 1, 200000, 90, 4, 2, 3);

-- SELECT * FROM host_info;
-- SELECT * FROM host_usage;

SELECT cpu_number,
       id,
       total_mem
       -- MAX(total_mem) OVER (PARTITION BY cpu_number ORDER BY total_mem DESC) AS total_mem
FROM host_info
-- GROUP BY cpu_number, total_mem, id
ORDER BY cpu_number, total_mem DESC;

CREATE OR REPLACE FUNCTION round5(ts timestamp) RETURNS timestamp AS
$$
BEGIN
    RETURN date_trunc('hour', ts) + date_part('minute', ts):: int / 5 * interval '5 min';
END;
$$
    LANGUAGE PLPGSQL;

CREATE OR REPLACE FUNCTION memPercent(memFree integer, memTotal integer)
    RETURNS integer
    AS
$$
BEGIN
    RETURN memTotal - memFree;
END;
$$
    LANGUAGE plpgsql;

SELECT host_id,
       round5(host_usage.timestamp) AS time,
       AVG(memPercent(memory_free, host_info.total_mem))
FROM host_usage
INNER JOIN host_info
    ON host_info.id = host_usage.host_id
GROUP BY time, host_id;

-- SELECT host_id, host_name, timestamp,avg_used_mem_percentage
-- FROM host_usage