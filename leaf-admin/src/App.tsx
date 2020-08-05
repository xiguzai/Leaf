import React from 'react';
import { Tabs } from 'antd';
import './App.css';
import DB from './DB';
import Cache from './Cache';

const { TabPane } = Tabs;

const App: React.FC = () => {
  return (
    <div className="App">
      <h1 style={{ paddingTop: '0.5em', marginBottom: 0 }}>Leaf Admin</h1>
      <Tabs defaultActiveKey="Cache">
        <TabPane tab="Cache" key="Cache">
          <Cache />
        </TabPane>
        <TabPane tab="DB" key="DB">
          <DB />
        </TabPane>
      </Tabs>
    </div>
  );
};

export default App;
