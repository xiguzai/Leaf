import React, { useState, useEffect } from 'react';
import { Table, Button, Spin } from 'antd';

interface Data {
  key: string;
  initOk: string;
  nextReady: string;
  pos: string;
  value0: string;
  max0: string;
  step0: string;
  value1: string;
  max1: string;
  step1: string;
}

export default function Cache() {
  const [data, setData] = useState(new Array<Data>());
  const [reloading, setReloading] = useState(false);
  const columns = [
    {
      title: 'Name',
      dataIndex: 'key',
      key: 'key'
    },
    {
      title: 'Init',
      dataIndex: 'initOk',
      key: 'initOk'
    },
    {
      title: 'Next',
      dataIndex: 'nextReady',
      key: 'nextReady'
    },
    {
      title: 'Pos',
      key: 'pos',
      dataIndex: 'pos'
    },
    {
      title: 'Value0',
      key: 'value0',
      dataIndex: 'value0'
    },
    {
      title: 'Max0',
      key: 'max0',
      dataIndex: 'max0'
    },
    {
      title: 'Step0',
      key: 'step0',
      dataIndex: 'step0'
    },
    {
      title: 'Value1',
      key: 'value1',
      dataIndex: 'value1'
    },
    {
      title: 'Max1',
      key: 'max1',
      dataIndex: 'max1'
    },
    {
      title: 'Step1',
      key: 'step1',
      dataIndex: 'step1'
    }
  ];

  const fetchData = () =>
    fetch('/admin/cache')
      .then(resp => resp.json())
      .then(resp => {
        if (resp.status === 200) {
          setData(resp.data);
        }
      });

  useEffect(() => {
    fetchData();
  }, []);

  const handleReload = (e: React.MouseEvent<HTMLElement, MouseEvent>) => {
    e.preventDefault();
    setReloading(true);
    setTimeout(() => {
      fetchData().then(() => setReloading(false));
    }, 500);
  };

  return (
    <div>
      <div style={{ marginBottom: '0.5em' }}>
        <Button loading={reloading} onClick={handleReload}>
          {reloading ? 'Reloading' : 'Reload'}
        </Button>
      </div>
      <Spin tip="Loading..." delay={100} spinning={reloading}>
        <Table columns={columns} dataSource={data} pagination={false} />
      </Spin>
    </div>
  );
}
