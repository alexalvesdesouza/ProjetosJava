alter table public.luf_jogo drop column data_criacao;
alter table public.luf_jogo add column data_criacao timestamp;

alter table public.luf_jogo drop column data_atualizacao;
alter table public.luf_jogo add column data_atualizacao timestamp;
