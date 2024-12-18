--
-- PostgreSQL database dump
--

-- Dumped from database version 12.5
-- Dumped by pg_dump version 14.2

-- Started on 2024-11-07 16:35:48

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2 (class 3079 OID 10366193)
-- Name: uuid-ossp; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;


--
-- TOC entry 2929 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION "uuid-ossp"; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 206 (class 1259 OID 10366246)
-- Name: attach; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.attach (
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    filename character varying(255) NOT NULL,
    mimetype character varying(255) NOT NULL,
    foldername character varying(255),
    compressed boolean DEFAULT false NOT NULL,
    thumbnailable boolean DEFAULT false NOT NULL,
    filesize bigint NOT NULL,
    author uuid,
    att_byte boolean NOT NULL,
    created timestamp without time zone DEFAULT now() NOT NULL,
    updated timestamp without time zone
);


ALTER TABLE public.attach OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 10366257)
-- Name: attach_link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.attach_link (
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    attach_id uuid NOT NULL,
    ref_id uuid NOT NULL
);


ALTER TABLE public.attach_link OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 10366268)
-- Name: cfi; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cfi (
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    codigo integer NOT NULL,
    cnpj_cpf character varying(18) NOT NULL,
    nome character varying(100) NOT NULL
);


ALTER TABLE public.cfi OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 10366274)
-- Name: cfr; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cfr (
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    codigo integer NOT NULL,
    cnpj_cpf character varying(18) NOT NULL,
    nome character varying(60) NOT NULL,
    contato_cobranca character varying(100),
    email_cobranca character varying(255)
);


ALTER TABLE public.cfr OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 10366280)
-- Name: cre; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cre (
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    filial_id uuid NOT NULL,
    cliente_id uuid NOT NULL,
    doc_type character varying(45) NOT NULL,
    documento character varying(45) NOT NULL,
    data_emissao date NOT NULL,
    data_vencimento date NOT NULL,
    valor_documento numeric(18,2) DEFAULT 0 NOT NULL,
    valor_aberto numeric(18,2) DEFAULT 0 NOT NULL,
    status character varying(45) NOT NULL,
    created timestamp without time zone DEFAULT now() NOT NULL,
    updated timestamp without time zone
);


ALTER TABLE public.cre OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 10366204)
-- Name: ecob_account; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ecob_account (
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    name character varying(255) NOT NULL,
    provider character varying(45) NOT NULL,
    status character varying(45) NOT NULL,
    settings jsonb NOT NULL,
    created timestamp without time zone DEFAULT now() NOT NULL,
    updated timestamp without time zone
);


ALTER TABLE public.ecob_account OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 10366301)
-- Name: ecob_inbox; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ecob_inbox (
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    account_id uuid NOT NULL,
    reply_to character varying(255),
    recipients_to character varying(255)[],
    recipients_cc character varying(255)[],
    recipients_bcc character varying(255)[],
    priority character varying(45),
    subject character varying(255) NOT NULL,
    body_format character varying(45) NOT NULL,
    body_text text,
    created timestamp without time zone DEFAULT now() NOT NULL,
    scheduled timestamp without time zone NOT NULL,
    processed_at timestamp without time zone,
    status character varying(45) NOT NULL,
    error_message text
);


ALTER TABLE public.ecob_inbox OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 10366316)
-- Name: ecob_inbox_link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ecob_inbox_link (
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    inbox_id uuid NOT NULL,
    ref_id uuid NOT NULL
);


ALTER TABLE public.ecob_inbox_link OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 10366229)
-- Name: ecob_msg; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ecob_msg (
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    name character varying(255) NOT NULL,
    date_rule character varying(45) NOT NULL,
    date_index character varying(45) NOT NULL,
    selected_times character varying(5)[] NOT NULL,
    selected_days smallint[] NOT NULL,
    doc_types character varying(45)[] NOT NULL,
    doc_status character varying(45)[] NOT NULL,
    include_attachments boolean DEFAULT false NOT NULL,
    model_id uuid,
    created timestamp without time zone DEFAULT now() NOT NULL,
    updated timestamp without time zone,
    status character varying(45) NOT NULL
);


ALTER TABLE public.ecob_msg OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 10366214)
-- Name: ecob_msg_model; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ecob_msg_model (
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    action character varying(45) NOT NULL,
    account_id uuid NOT NULL,
    recipient_type character varying(45) NOT NULL,
    reply_to character varying(255),
    cc character varying(255)[],
    bcc character varying(255)[],
    subject character varying(255) NOT NULL,
    body_text text NOT NULL,
    created timestamp without time zone DEFAULT now() NOT NULL,
    updated timestamp without time zone
);


ALTER TABLE public.ecob_msg_model OWNER TO postgres;

--
-- TOC entry 2917 (class 0 OID 10366246)
-- Dependencies: 206
-- Data for Name: attach; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.attach (id, filename, mimetype, foldername, compressed, thumbnailable, filesize, author, att_byte, created, updated) VALUES ('1f820e61-7571-447f-a7f1-8040ef818b84', 'boleto1.pdf', 'application/pdf', '', false, false, 26020, NULL, false, '2024-11-07 15:41:46.085443', NULL);
INSERT INTO public.attach (id, filename, mimetype, foldername, compressed, thumbnailable, filesize, author, att_byte, created, updated) VALUES ('f41ccd98-fcb4-4768-adff-59cdd06ad328', 'boleto2.pdf', 'application/pdf', '', false, false, 26010, NULL, false, '2024-11-07 15:41:46.085443', NULL);
INSERT INTO public.attach (id, filename, mimetype, foldername, compressed, thumbnailable, filesize, author, att_byte, created, updated) VALUES ('c1a97d6f-873a-487b-89a4-873ccc55ea32', 'boleto3.pdf', 'application/pdf', '', false, false, 25982, NULL, false, '2024-11-07 15:41:46.085443', NULL);
INSERT INTO public.attach (id, filename, mimetype, foldername, compressed, thumbnailable, filesize, author, att_byte, created, updated) VALUES ('0e4dd9c3-db17-4c5d-9f14-31acaba631ac', 'boleto4.pdf', 'application/pdf', '', false, false, 25944, NULL, false, '2024-11-07 15:41:46.085443', NULL);
INSERT INTO public.attach (id, filename, mimetype, foldername, compressed, thumbnailable, filesize, author, att_byte, created, updated) VALUES ('4ef19b0f-353e-4fe0-9976-348837914e7f', 'boleto5.pdf', 'application/pdf', '', false, false, 25966, NULL, false, '2024-11-07 15:41:46.085443', NULL);
INSERT INTO public.attach (id, filename, mimetype, foldername, compressed, thumbnailable, filesize, author, att_byte, created, updated) VALUES ('26427427-ab63-43d3-bdcc-42aa9475c1b9', 'boleto6.pdf', 'application/pdf', '', false, false, 26004, NULL, false, '2024-11-07 15:41:46.085443', NULL);
INSERT INTO public.attach (id, filename, mimetype, foldername, compressed, thumbnailable, filesize, author, att_byte, created, updated) VALUES ('0842a179-6603-433a-abd6-b7b1864b1865', 'boleto7.pdf', 'application/pdf', '', false, false, 25844, NULL, false, '2024-11-07 15:41:46.085443', NULL);


--
-- TOC entry 2918 (class 0 OID 10366257)
-- Dependencies: 207
-- Data for Name: attach_link; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.attach_link (id, attach_id, ref_id) VALUES ('6d58d91b-e1a1-4936-9a64-ce207fe0ba29', '1f820e61-7571-447f-a7f1-8040ef818b84', '5831633b-3c67-4a61-80c3-6d38002f3bd0');
INSERT INTO public.attach_link (id, attach_id, ref_id) VALUES ('314feb66-e0c7-4ed6-b838-22e2abd234a4', 'f41ccd98-fcb4-4768-adff-59cdd06ad328', 'c8aac466-dcd9-4386-83d5-3d5f993c5f24');
INSERT INTO public.attach_link (id, attach_id, ref_id) VALUES ('095c0815-57c7-433e-b163-0af7f6fcdbbc', 'c1a97d6f-873a-487b-89a4-873ccc55ea32', '44aca1a4-7f17-4e3f-8ca5-4b820280e5f5');
INSERT INTO public.attach_link (id, attach_id, ref_id) VALUES ('8b971a4b-d9b0-424e-9bd1-6f8a7b72a555', '0e4dd9c3-db17-4c5d-9f14-31acaba631ac', '05de4c2a-64dd-466f-acfd-512c466ae5ce');
INSERT INTO public.attach_link (id, attach_id, ref_id) VALUES ('084f5de6-83fa-48ef-9ef3-6e0169e896a2', '4ef19b0f-353e-4fe0-9976-348837914e7f', '7b640b08-0b59-4e84-a00c-857ae7adccdc');
INSERT INTO public.attach_link (id, attach_id, ref_id) VALUES ('0fe70645-62ed-45d0-8043-5da4029b820e', '26427427-ab63-43d3-bdcc-42aa9475c1b9', '4e64a46f-b64e-4772-9134-51879b21b143');
INSERT INTO public.attach_link (id, attach_id, ref_id) VALUES ('55d89fe6-0b93-4e4a-a3b7-803a73ceacff', '0842a179-6603-433a-abd6-b7b1864b1865', '4e64a46f-b64e-4772-9134-51879b21b143');


--
-- TOC entry 2919 (class 0 OID 10366268)
-- Dependencies: 208
-- Data for Name: cfi; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cfi (id, codigo, cnpj_cpf, nome) VALUES ('cd8ef01f-2b79-42ca-a206-987e0cf8e2b0', 1, '07.626.135/0001-06', 'TI9 SISTEMAS DE INFORMACAO LTDA');


--
-- TOC entry 2920 (class 0 OID 10366274)
-- Dependencies: 209
-- Data for Name: cfr; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cfr (id, codigo, cnpj_cpf, nome, contato_cobranca, email_cobranca) VALUES ('615126a8-9dd2-4d29-b8f9-9327f6f936a9', 1, '07.626.135/0001-06', 'CLIENTE TESTE 0011', 'ANDRE VACARI', 'andre.vacari@ti9.com.br');
INSERT INTO public.cfr (id, codigo, cnpj_cpf, nome, contato_cobranca, email_cobranca) VALUES ('5db289a7-bccf-419a-8d0d-23c1fac13735', 2, '07.626.135/0099-99', 'CLIENTE TESTE 0002', 'RUTI CRUZ', 'ruti.cruz@ti9.com.br');


--
-- TOC entry 2921 (class 0 OID 10366280)
-- Dependencies: 210
-- Data for Name: cre; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cre (id, filial_id, cliente_id, doc_type, documento, data_emissao, data_vencimento, valor_documento, valor_aberto, status, created, updated) VALUES ('5831633b-3c67-4a61-80c3-6d38002f3bd0', 'cd8ef01f-2b79-42ca-a206-987e0cf8e2b0', '615126a8-9dd2-4d29-b8f9-9327f6f936a9', 'FATURA', '123456', '2024-11-01', '2024-11-30', 1250.55, 1250.55, 'ABERTO', '2024-11-07 15:27:47.770256', NULL);
INSERT INTO public.cre (id, filial_id, cliente_id, doc_type, documento, data_emissao, data_vencimento, valor_documento, valor_aberto, status, created, updated) VALUES ('c8aac466-dcd9-4386-83d5-3d5f993c5f24', 'cd8ef01f-2b79-42ca-a206-987e0cf8e2b0', '615126a8-9dd2-4d29-b8f9-9327f6f936a9', 'NF', '000001', '2024-11-01', '2024-12-17', 123456.55, 123456.55, 'ABERTO', '2024-11-07 15:29:03.306039', NULL);
INSERT INTO public.cre (id, filial_id, cliente_id, doc_type, documento, data_emissao, data_vencimento, valor_documento, valor_aberto, status, created, updated) VALUES ('44aca1a4-7f17-4e3f-8ca5-4b820280e5f5', 'cd8ef01f-2b79-42ca-a206-987e0cf8e2b0', '615126a8-9dd2-4d29-b8f9-9327f6f936a9', 'NF', '000002', '2024-11-01', '2024-12-22', 700.55, 500.10, 'PAGO_PARCIAL', '2024-11-07 15:29:03.306039', NULL);
INSERT INTO public.cre (id, filial_id, cliente_id, doc_type, documento, data_emissao, data_vencimento, valor_documento, valor_aberto, status, created, updated) VALUES ('05de4c2a-64dd-466f-acfd-512c466ae5ce', 'cd8ef01f-2b79-42ca-a206-987e0cf8e2b0', '5db289a7-bccf-419a-8d0d-23c1fac13735', 'FATURA', '111111', '2024-10-12', '2024-11-20', 1250.99, 1250.99, 'ABERTO', '2024-11-07 15:30:59.162742', NULL);
INSERT INTO public.cre (id, filial_id, cliente_id, doc_type, documento, data_emissao, data_vencimento, valor_documento, valor_aberto, status, created, updated) VALUES ('7b640b08-0b59-4e84-a00c-857ae7adccdc', 'cd8ef01f-2b79-42ca-a206-987e0cf8e2b0', '5db289a7-bccf-419a-8d0d-23c1fac13735', 'FATURA', '000010', '2024-10-13', '2024-12-01', 123456.99, 123456.99, 'ABERTO', '2024-11-07 15:30:59.162742', NULL);
INSERT INTO public.cre (id, filial_id, cliente_id, doc_type, documento, data_emissao, data_vencimento, valor_documento, valor_aberto, status, created, updated) VALUES ('4e64a46f-b64e-4772-9134-51879b21b143', 'cd8ef01f-2b79-42ca-a206-987e0cf8e2b0', '5db289a7-bccf-419a-8d0d-23c1fac13735', 'FATURA', '000020', '2024-10-14', '2024-12-07', 200.55, 100.10, 'PAGO_PARCIAL', '2024-11-07 15:30:59.162742', NULL);


--
-- TOC entry 2914 (class 0 OID 10366204)
-- Dependencies: 203
-- Data for Name: ecob_account; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2922 (class 0 OID 10366301)
-- Dependencies: 211
-- Data for Name: ecob_inbox; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2923 (class 0 OID 10366316)
-- Dependencies: 212
-- Data for Name: ecob_inbox_link; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2916 (class 0 OID 10366229)
-- Dependencies: 205
-- Data for Name: ecob_msg; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2915 (class 0 OID 10366214)
-- Dependencies: 204
-- Data for Name: ecob_msg_model; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2768 (class 2606 OID 10366262)
-- Name: attach_link attach_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.attach_link
    ADD CONSTRAINT attach_link_pkey PRIMARY KEY (id);


--
-- TOC entry 2766 (class 2606 OID 10366256)
-- Name: attach attach_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.attach
    ADD CONSTRAINT attach_pkey PRIMARY KEY (id);


--
-- TOC entry 2770 (class 2606 OID 10366273)
-- Name: cfi cfi_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cfi
    ADD CONSTRAINT cfi_pkey PRIMARY KEY (id);


--
-- TOC entry 2772 (class 2606 OID 10366279)
-- Name: cfr cfr_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cfr
    ADD CONSTRAINT cfr_pkey PRIMARY KEY (id);


--
-- TOC entry 2774 (class 2606 OID 10366288)
-- Name: cre cre_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cre
    ADD CONSTRAINT cre_pkey PRIMARY KEY (id);


--
-- TOC entry 2759 (class 2606 OID 10366213)
-- Name: ecob_account ecob_account_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ecob_account
    ADD CONSTRAINT ecob_account_pkey PRIMARY KEY (id);


--
-- TOC entry 2780 (class 2606 OID 10366321)
-- Name: ecob_inbox_link ecob_inbox_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ecob_inbox_link
    ADD CONSTRAINT ecob_inbox_link_pkey PRIMARY KEY (id);


--
-- TOC entry 2778 (class 2606 OID 10366310)
-- Name: ecob_inbox ecob_inbox_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ecob_inbox
    ADD CONSTRAINT ecob_inbox_pkey PRIMARY KEY (id);


--
-- TOC entry 2761 (class 2606 OID 10366223)
-- Name: ecob_msg_model ecob_msg_model_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ecob_msg_model
    ADD CONSTRAINT ecob_msg_model_pkey PRIMARY KEY (id);


--
-- TOC entry 2763 (class 2606 OID 10366239)
-- Name: ecob_msg ecob_msg_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ecob_msg
    ADD CONSTRAINT ecob_msg_pkey PRIMARY KEY (id);


--
-- TOC entry 2775 (class 1259 OID 10366300)
-- Name: idx_cre_status_tipo_doc1; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_cre_status_tipo_doc1 ON public.cre USING btree (status, doc_type);


--
-- TOC entry 2764 (class 1259 OID 10366245)
-- Name: idx_ecob_msg_status1; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_ecob_msg_status1 ON public.ecob_msg USING btree (doc_status);


--
-- TOC entry 2776 (class 1259 OID 10366299)
-- Name: idx_tipo_doc; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_tipo_doc ON public.cre USING btree (doc_type);


--
-- TOC entry 2783 (class 2606 OID 10366263)
-- Name: attach_link fk_attach_link_attach1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.attach_link
    ADD CONSTRAINT fk_attach_link_attach1 FOREIGN KEY (attach_id) REFERENCES public.attach(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2784 (class 2606 OID 10366289)
-- Name: cre fk_cre_cfi1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cre
    ADD CONSTRAINT fk_cre_cfi1 FOREIGN KEY (filial_id) REFERENCES public.cfi(id) ON UPDATE CASCADE;


--
-- TOC entry 2785 (class 2606 OID 10366294)
-- Name: cre fk_cre_cfr1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cre
    ADD CONSTRAINT fk_cre_cfr1 FOREIGN KEY (cliente_id) REFERENCES public.cfr(id) ON UPDATE CASCADE;


--
-- TOC entry 2786 (class 2606 OID 10366311)
-- Name: ecob_inbox fk_ecob_inbox_ecob_account1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ecob_inbox
    ADD CONSTRAINT fk_ecob_inbox_ecob_account1 FOREIGN KEY (account_id) REFERENCES public.ecob_account(id) ON UPDATE CASCADE;


--
-- TOC entry 2787 (class 2606 OID 10366322)
-- Name: ecob_inbox_link fk_ecob_inbox_ref_ecob_inbox1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ecob_inbox_link
    ADD CONSTRAINT fk_ecob_inbox_ref_ecob_inbox1 FOREIGN KEY (inbox_id) REFERENCES public.ecob_inbox(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2782 (class 2606 OID 10366240)
-- Name: ecob_msg fk_ecob_msg_ecob_msg_modelo1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ecob_msg
    ADD CONSTRAINT fk_ecob_msg_ecob_msg_modelo1 FOREIGN KEY (model_id) REFERENCES public.ecob_msg_model(id) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- TOC entry 2781 (class 2606 OID 10366224)
-- Name: ecob_msg_model fk_ecob_msg_modelo_ecob_account1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ecob_msg_model
    ADD CONSTRAINT fk_ecob_msg_modelo_ecob_account1 FOREIGN KEY (account_id) REFERENCES public.ecob_account(id) ON UPDATE CASCADE;



INSERT INTO public.ecob_account (id,name,provider,status,settings,created,updated) VALUES (
	 '36734e49-c3a2-4caa-b56a-b7233fe4f987'::uuid,
	 'Poly Account',
	 'GMAIL',
	 'ACTIVE',
	 '{"scope": "https://www.googleapis.com/auth/gmail.send https://mail.google.com/", "expires_in": 3599, "token_type": "Bearer", "access_token": "ya29.a0AeDClZA-dAr4yWaX3mFhdOIJhQl0TSEli7_iAHc0n7HkSWSbsjJCc_088SK9i_cT2cUT8xOMvO8KomSwloo5bUUXPWKxXvt4bmJSLn-71X1H3VRq2zNzNjUVGbywHZqop79uu16ZxwhUqGqX87YZqwcPijO41i1VQMTp5eZIaCgYKAV8SARISFQHGX2MiIM-gIPD4VpeLUCzVQauftg0175", "refresh_token": "1//046pSJKArkthgCgYIARAAGAQSNwF-L9IrphSVmTGpcyZ64OlvURVjtnINi8jOsMO_xn-mOw6udCGAeF01ok-NhUF_Jsp_OYgzV1E"}',
	 '2024-12-04 21:25:19.08135',
	 NULL
 );

 INSERT INTO public.ecob_msg_model (id,"action",account_id,recipient_type,reply_to,cc,bcc,subject,body_text,created,updated) VALUES (
    'ffd9c55f-5c84-4f26-9159-c8b09137e187'::uuid,
    'EMAIL',
    '36734e49-c3a2-4caa-b56a-b7233fe4f987'::uuid,
    'COBRANCA',
    'poly@gmail.com',
    '{poly@gmail.com}',
    '{}',
    'Ola mundo',
    '<h1>Ola mundo body</h1><br><p>Email test!</p>',
    '2024-12-04 21:52:35.297949',
    NULL
);

INSERT INTO public.ecob_msg (id,name,date_rule,date_index,selected_times,selected_days,doc_types,doc_status,include_attachments,model_id,created,updated,status) VALUES
	 ('6987524a-b6b9-4361-93a2-19fd02387024'::uuid,'Vencimento -1','DIAS_CORRIDOS','VENCIMENTO','{01:00,01:05,01:10,01:15,01:20,01:25,01:30,01:35,01:40,01:45,01:50,01:55,02:00,02:05,02:10,02:15,02:20,02:25,02:30,02:35,02:40,02:45,02:50,02:55,03:00,03:05,03:10,03:15,03:20,03:25,03:30,03:35,03:40,03:45,03:50,03:55,04:00,04:05,04:10,04:15,04:20,04:25,04:30,04:35,04:40,04:45,04:50,04:55,05:00,05:05,05:10,05:15,05:20,05:25,05:30,05:35,05:40,05:45,05:50,05:55,06:00,06:05,06:10,06:15,06:20,06:25,06:30,06:35,06:40,06:45,06:50,06:55,07:00,07:05,07:10,07:15,07:20,07:25,07:30,07:35,07:40,07:45,07:50,07:55,08:00,08:05,08:10,08:15,08:20,08:25,08:30,08:35,08:40,08:45,08:50,09:55,09:00,09:05,09:10,09:15,09:20,09:25,09:30,09:35,09:40,09:45,09:50,09:55,10:00,10:05,10:10,10:15,10:20,10:25,10:30,10:35,10:40,10:45,10:50,10:55,11:00,11:05,11:10,11:15,11:20,11:25,11:30,11:35,11:40,11:45,11:50,11:55,12:00,12:05,12:10,12:15,12:20,12:25,12:30,12:35,12:40,12:45,12:50,12:55,13:00,13:05,13:10,13:15,13:20,13:25,13:30,13:35,13:40,13:45,13:50,13:55,14:00,14:05,14:10,14:15,14:20,14:25,14:30,14:35,14:40,14:45,14:50,14:55,15:00,15:05,15:10,15:15,15:20,15:25,15:30,15:35,15:40,15:45,15:50,15:55,16:00,16:05,16:10,16:15,16:20,16:25,16:30,16:35,16:40,16:45,16:50,16:55,17:00,17:05,17:10,17:15,17:20,17:25,17:30,17:35,17:40,17:45,17:50,17:55,18:00,18:05,18:10,18:15,18:20,18:25,18:30,18:35,18:40,18:45,18:50,18:55,19:00,19:05,19:10,19:15,19:20,19:25,19:30,19:35,19:40,19:45,19:50,19:55,20:00,20:05,20:10,20:15,20:20,20:25,20:30,20:35,20:40,20:45,20:50,20:55,21:00,21:05,21:10,21:15,21:20,21:25,21:30,21:35,21:40,21:45,21:50,21:55,22:00,22:05,22:10,22:15,22:20,22:25,22:30,22:35,22:40,22:45,22:50,22:55,23:00,23:05,23:10,23:15,23:20,23:25,23:30,23:35,23:40,23:45,23:50,23:55,00:00,00:05,00:10,00:15,00:20,00:25,00:30,00:35,00:40,00:45,00:50,00:55}','{-1}','{Fatura,NF}','{ABERTO}',true,'ffd9c55f-5c84-4f26-9159-c8b09137e187'::uuid,'2024-12-16 19:49:17.618811',NULL,'ACTIVE'),
	 ('075be6c1-9e0c-4ba6-ba8f-4c7730e4041b'::uuid,'No dia do vencimento','DIAS_CORRIDOS','VENCIMENTO','{01:00,01:05,01:10,01:15,01:20,01:25,01:30,01:35,01:40,01:45,01:50,01:55,02:00,02:05,02:10,02:15,02:20,02:25,02:30,02:35,02:40,02:45,02:50,02:55,03:00,03:05,03:10,03:15,03:20,03:25,03:30,03:35,03:40,03:45,03:50,03:55,04:00,04:05,04:10,04:15,04:20,04:25,04:30,04:35,04:40,04:45,04:50,04:55,05:00,05:05,05:10,05:15,05:20,05:25,05:30,05:35,05:40,05:45,05:50,05:55,06:00,06:05,06:10,06:15,06:20,06:25,06:30,06:35,06:40,06:45,06:50,06:55,07:00,07:05,07:10,07:15,07:20,07:25,07:30,07:35,07:40,07:45,07:50,07:55,08:00,08:05,08:10,08:15,08:20,08:25,08:30,08:35,08:40,08:45,08:50,09:55,09:00,09:05,09:10,09:15,09:20,09:25,09:30,09:35,09:40,09:45,09:50,09:55,10:00,10:05,10:10,10:15,10:20,10:25,10:30,10:35,10:40,10:45,10:50,10:55,11:00,11:05,11:10,11:15,11:20,11:25,11:30,11:35,11:40,11:45,11:50,11:55,12:00,12:05,12:10,12:15,12:20,12:25,12:30,12:35,12:40,12:45,12:50,12:55,13:00,13:05,13:10,13:15,13:20,13:25,13:30,13:35,13:40,13:45,13:50,13:55,14:00,14:05,14:10,14:15,14:20,14:25,14:30,14:35,14:40,14:45,14:50,14:55,15:00,15:05,15:10,15:15,15:20,15:25,15:30,15:35,15:40,15:45,15:50,15:55,16:00,16:05,16:10,16:15,16:20,16:25,16:30,16:35,16:40,16:45,16:50,16:55,17:00,17:05,17:10,17:15,17:20,17:25,17:30,17:35,17:40,17:45,17:50,17:55,18:00,18:05,18:10,18:15,18:20,18:25,18:30,18:35,18:40,18:45,18:50,18:55,19:00,19:05,19:10,19:15,19:20,19:25,19:30,19:35,19:40,19:45,19:50,19:55,20:00,20:05,20:10,20:15,20:20,20:25,20:30,20:35,20:40,20:45,20:50,20:55,21:00,21:05,21:10,21:15,21:20,21:25,21:30,21:35,21:40,21:45,21:50,21:55,22:00,22:05,22:10,22:15,22:20,22:25,22:30,22:35,22:40,22:45,22:50,22:55,23:00,23:05,23:10,23:15,23:20,23:25,23:30,23:35,23:40,23:45,23:50,23:55,00:00,00:05,00:10,00:15,00:20,00:25,00:30,00:35,00:40,00:45,00:50,00:55}','{0}','{Fatura,NF}','{ABERTO}',true,'ffd9c55f-5c84-4f26-9159-c8b09137e187'::uuid,'2024-12-16 19:49:17.618811',NULL,'ACTIVE'),
	 ('8f72b7f4-47b3-489a-91a2-73a8ee4be0b3'::uuid,'Vencimento -2','DIAS_CORRIDOS','VENCIMENTO','{01:00,01:05,01:10,01:15,01:20,01:25,01:30,01:35,01:40,01:45,01:50,01:55,02:00,02:05,02:10,02:15,02:20,02:25,02:30,02:35,02:40,02:45,02:50,02:55,03:00,03:05,03:10,03:15,03:20,03:25,03:30,03:35,03:40,03:45,03:50,03:55,04:00,04:05,04:10,04:15,04:20,04:25,04:30,04:35,04:40,04:45,04:50,04:55,05:00,05:05,05:10,05:15,05:20,05:25,05:30,05:35,05:40,05:45,05:50,05:55,06:00,06:05,06:10,06:15,06:20,06:25,06:30,06:35,06:40,06:45,06:50,06:55,07:00,07:05,07:10,07:15,07:20,07:25,07:30,07:35,07:40,07:45,07:50,07:55,08:00,08:05,08:10,08:15,08:20,08:25,08:30,08:35,08:40,08:45,08:50,09:55,09:00,09:05,09:10,09:15,09:20,09:25,09:30,09:35,09:40,09:45,09:50,09:55,10:00,10:05,10:10,10:15,10:20,10:25,10:30,10:35,10:40,10:45,10:50,10:55,11:00,11:05,11:10,11:15,11:20,11:25,11:30,11:35,11:40,11:45,11:50,11:55,12:00,12:05,12:10,12:15,12:20,12:25,12:30,12:35,12:40,12:45,12:50,12:55,13:00,13:05,13:10,13:15,13:20,13:25,13:30,13:35,13:40,13:45,13:50,13:55,14:00,14:05,14:10,14:15,14:20,14:25,14:30,14:35,14:40,14:45,14:50,14:55,15:00,15:05,15:10,15:15,15:20,15:25,15:30,15:35,15:40,15:45,15:50,15:55,16:00,16:05,16:10,16:15,16:20,16:25,16:30,16:35,16:40,16:45,16:50,16:55,17:00,17:05,17:10,17:15,17:20,17:25,17:30,17:35,17:40,17:45,17:50,17:55,18:00,18:05,18:10,18:15,18:20,18:25,18:30,18:35,18:40,18:45,18:50,18:55,19:00,19:05,19:10,19:15,19:20,19:25,19:30,19:35,19:40,19:45,19:50,19:55,20:00,20:05,20:10,20:15,20:20,20:25,20:30,20:35,20:40,20:45,20:50,20:55,21:00,21:05,21:10,21:15,21:20,21:25,21:30,21:35,21:40,21:45,21:50,21:55,22:00,22:05,22:10,22:15,22:20,22:25,22:30,22:35,22:40,22:45,22:50,22:55,23:00,23:05,23:10,23:15,23:20,23:25,23:30,23:35,23:40,23:45,23:50,23:55,00:00,00:05,00:10,00:15,00:20,00:25,00:30,00:35,00:40,00:45,00:50,00:55}','{0}','{Fatura,NF}','{ABERTO}',true,'ffd9c55f-5c84-4f26-9159-c8b09137e187'::uuid,'2024-12-16 19:49:17.618811',NULL,'ACTIVE');


INSERT INTO public.ecob_inbox (id,account_id,reply_to,recipients_to,recipients_cc,recipients_bcc,priority,subject,body_format,body_text,created,scheduled,processed_at,status,error_message) VALUES
	 ('e0383603-962c-4843-ae8d-ee75e8ef890b'::uuid,'36734e49-c3a2-4caa-b56a-b7233fe4f987'::uuid,'poly','{poly}','{poly}','{poly}','HIGH','poly','PLAIN','poly','2024-12-16 21:10:18.817251','2024-12-16 19:49:17.618','2024-12-16 19:49:17.618','SCHEDULED',NULL);


INSERT INTO public.ecob_inbox_link (id,inbox_id,ref_id) VALUES
	 ('da73f39b-a12e-4c65-a4d0-b7f2dc6a0d95'::uuid,'e0383603-962c-4843-ae8d-ee75e8ef890b'::uuid,'1f820e61-7571-447f-a7f1-8040ef818b84'::uuid),
	 ('6b025080-7233-44f9-8178-38a26579d629'::uuid,'e0383603-962c-4843-ae8d-ee75e8ef890b'::uuid,'c8aac466-dcd9-4386-83d5-3d5f993c5f24'::uuid),
	 ('e34c0908-0f08-4427-aa55-5cb463113f8b'::uuid,'e0383603-962c-4843-ae8d-ee75e8ef890b'::uuid,'44aca1a4-7f17-4e3f-8ca5-4b820280e5f5'::uuid),
	 ('c980696b-e689-4427-93ba-97d226168b5b'::uuid,'e0383603-962c-4843-ae8d-ee75e8ef890b'::uuid,'05de4c2a-64dd-466f-acfd-512c466ae5ce'::uuid),
	 ('859ac78f-a93f-4046-a608-a25245e592f5'::uuid,'e0383603-962c-4843-ae8d-ee75e8ef890b'::uuid,'7b640b08-0b59-4e84-a00c-857ae7adccdc'::uuid),
	 ('efe4a65d-05af-4d00-9736-090141d43a47'::uuid,'e0383603-962c-4843-ae8d-ee75e8ef890b'::uuid,'4e64a46f-b64e-4772-9134-51879b21b143'::uuid),
	 ('fce0b177-04d9-4ad1-ae30-9e4c7f629944'::uuid,'e0383603-962c-4843-ae8d-ee75e8ef890b'::uuid,'4e64a46f-b64e-4772-9134-51879b21b143'::uuid);




-- Completed on 2024-11-07 16:35:48

--
-- PostgreSQL database dump complete
--

