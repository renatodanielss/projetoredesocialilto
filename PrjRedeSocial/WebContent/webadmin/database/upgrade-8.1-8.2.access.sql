
create index c_created_idx on content (created);

create index c_updated_idx on content (updated);

create index c_published_idx on content (published);

create index c_unpublished_idx on content (unpublished);

create index c_status_idx on content (status);

create index c_created_by_idx on content (created_by);

create index c_updated_by_idx on content (updated_by);

create index c_published_by_idx on content (published_by);

create index c_unpublished_by_idx on content (unpublished_by);

create index c_status_by_idx on content (status_by);

create index c_sched_publish_idx on content (scheduled_publish);

create index c_sched_unpublish_idx on content (scheduled_unpublish);

create index c_req_publish_idx on content (requested_publish);

create index c_req_unpublish_idx on content (requested_unpublish);

create index c_image1_idx on content (image1);

create index c_image2_idx on content (image2);

create index c_image3_idx on content (image3);

create index c_file1_idx on content (file1);

create index c_file2_idx on content (file2);

create index c_file3_idx on content (file3);

create index c_link1_idx on content (link1);

create index c_link2_idx on content (link2);

create index c_link3_idx on content (link3);



create index ca_created_idx on content_archive (created);

create index ca_updated_idx on content_archive (updated);

create index ca_published_idx on content_archive (published);

create index ca_unpublished_idx on content_archive (unpublished);

create index ca_status_idx on content_archive (status);

create index ca_created_by_idx on content_archive (created_by);

create index ca_updated_by_idx on content_archive (updated_by);

create index ca_published_by_idx on content_archive (published_by);

create index ca_unpublished_by_idx on content_archive (unpublished_by);

create index ca_status_by_idx on content_archive (status_by);

create index ca_sched_publish_idx on content_archive (scheduled_publish);

create index ca_sched_unpublish_idx on content_archive (scheduled_unpublish);

create index ca_req_publish_idx on content_archive (requested_publish);

create index ca_req_unpublish_idx on content_archive (requested_unpublish);

create index ca_image1_idx on content_archive (image1);

create index ca_image2_idx on content_archive (image2);

create index ca_image3_idx on content_archive (image3);

create index ca_file1_idx on content_archive (file1);

create index ca_file2_idx on content_archive (file2);

create index ca_file3_idx on content_archive (file3);

create index ca_link1_idx on content_archive (link1);

create index ca_link2_idx on content_archive (link2);

create index ca_link3_idx on content_archive (link3);



create index cp_created_idx on content_public (created);

create index cp_updated_idx on content_public (updated);

create index cp_published_idx on content_public (published);

create index cp_unpublished_idx on content_public (unpublished);

create index cp_status_idx on content_public (status);

create index cp_created_by_idx on content_public (created_by);

create index cp_updated_by_idx on content_public (updated_by);

create index cp_published_by_idx on content_public (published_by);

create index cp_unpublished_by_idx on content_public (unpublished_by);

create index cp_status_by_idx on content_public (status_by);

create index cp_sched_publish_idx on content_public (scheduled_publish);

create index cp_sched_unpublish_idx on content_public (scheduled_unpublish);

create index cp_req_publish_idx on content_public (requested_publish);

create index cp_req_unpublish_idx on content_public (requested_unpublish);

create index cp_image1_idx on content_public (image1);

create index cp_image2_idx on content_public (image2);

create index cp_image3_idx on content_public (image3);

create index cp_file1_idx on content_public (file1);

create index cp_file2_idx on content_public (file2);

create index cp_file3_idx on content_public (file3);

create index cp_link1_idx on content_public (link1);

create index cp_link2_idx on content_public (link2);

create index cp_link3_idx on content_public (link3);



create index u_s_p_email_idx on hosting (scheduled_publish_email);

create index u_s_n_email_idx on hosting (scheduled_notify_email);

create index u_s_u_email_idx on hosting (scheduled_unpublish_email);



alter table users add index_workspace text;



alter table content add doctype text;

alter table content_archive add doctype text;

alter table content_public add doctype text;

alter table contentgroups add doctype text;

alter table contenttypes add doctype text;

alter table productgroups add doctype text;

alter table producttypes add doctype text;

alter table websites add default_doctype text;



alter table content_elements add element_order integer;

alter table content_elements add element_params text;

alter table content_archive_elements add element_order integer;

alter table content_archive_elements add element_params text;

alter table content_public_elements add element_order integer;

alter table content_public_elements add element_params text;



alter table workflow add workflow_program text;



update config set configvalue='8.2' where configname='database_version';

