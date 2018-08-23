
insert into recs (id, doc) values ('@0', '{ "id":"@0",             "bar":"@3", "a":"x" }'::jsonb);
insert into recs (id, doc) values ('@1', '{ "id":"@1", "foo":"@0", "bar":"@2", "b":"x" }'::jsonb);
insert into recs (id, doc) values ('@2', '{ "id":"@2", "foo":"@0", "bar":"@3", "c":"x" }'::jsonb);
insert into recs (id, doc) values ('@3', '{ "id":"@3",                         "d":"x" }'::jsonb);
insert into recs (id, doc) values ('@4', '{ "id":"@4", "foo":"@3", "bar":"@2", "b":"x" }'::jsonb);
insert into recs (id, doc) values ('@5', '{ "id":"@5", "foo":"@3",             "e":"x" }'::jsonb);
insert into recs (id, doc) values ('@6', '{ "id":"@6","abc":{"x":12,"y":23}}'::jsonb);

insert into refs (tag, fromId, toRef) values ('foo', '@1', '@0');
insert into refs (tag, fromId, toRef) values ('foo', '@2', '@0');
insert into refs (tag, fromId, toRef) values ('foo', '@4', '@3');
insert into refs (tag, fromId, toRef) values ('foo', '@5', '@3');

insert into refs (tag, fromId, toRef) values ('bar', '@0', '@3');
insert into refs (tag, fromId, toRef) values ('bar', '@1', '@2');
insert into refs (tag, fromId, toRef) values ('bar', '@2', '@3');
insert into refs (tag, fromId, toRef) values ('bar', '@4', '@2');

select r0.doc from recs r0 where r0.doc ? 'a';

select r0.doc from recs r0 
join refs j1 on j1.fromId = r0.id and j1.tag = 'foo'
join recs r1 on j1.toRef  = r1.id and j1.tag = 'foo';

select r0.doc from recs r0 
join refs j1 on j1.fromId = r0.id and j1.tag = 'bar'
join recs r1 on j1.toRef  = r1.id and j1.tag = 'bar';

select r0.doc from recs r0 
join refs j1 on j1.fromId = r0.id and j1.tag = 'foo'
join recs r1 on j1.toRef  = r1.id and j1.tag = 'foo'
where r0.doc ? 'b' and r1.doc ? 'a';

select r0.doc from recs r0 
join refs j1 on j1.fromId = r0.id and j1.tag = 'bar'
join recs r1 on j1.toRef  = r1.id and j1.tag = 'bar'
join refs j2 on j2.fromId = r1.id and j2.tag = 'foo'
join recs r2 on j2.toRef  = r2.id and j2.tag = 'foo';

select r0.doc from recs r0 where r0.doc @> '{"foo":"@0"}'::jsonb;
