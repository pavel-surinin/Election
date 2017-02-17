function getMulti(self) {
  axios
  .get('district/multi/registered')
  .then(function(response){
    self.setState({districtMultiList : response.data});
  })
  .catch(function(err){
    console.error('ResultsListContainer.componentWillMount.axios',err);
  });
}
function getSingle(self) {
  axios
  .get('district/single/registered')
  .then(function(response){
    self.setState({districtSingleList : response.data, isLoading : false});
  })
  .catch(function(err){
    console.error('ResultsListContainer.componentWillMount.axios',err);
  });
}
function approve(self,type,id,name){
  axios
  .patch('/result/'+type+'/'+id+'/approve/')
  .then(function(response){
    getMulti(self);
    getSingle(self);
    self.setState({deletedResultsText: ''});
    self.setState({succesCreateText: name + ' balsai patvirtinti.'})
  })
  .catch(function(err){
    console.error('ResultsListContainer.onHandleApprove',err);
  });
}
function deleteVotes(self,type,id,name) {
  axios
  .delete('/result/'+type+'/'+id)
  .then(function(response){
    getMulti(self);
    getSingle(self);
    self.setState({succesCreateText: ''});
    self.setState({deletedResultsText : name + ' balsai atmesti.'});
  })
  .catch(function(err){
    console.error('ResultsListContainer.onHandleDelete',err);
  });
}

var ResultsListContainer = React.createClass({
  getInitialState: function() {
    return {
      districtMultiList : [],
      districtSingleList : [],
      multiEnabled : false,
      singleEnabled : false,
      succesCreateText : '',
      deletedResultsText : '',
    };
  },
  componentWillMount: function() {
    getMulti(this);
    getSingle(this);
  },
  onHandleRefresh : function(){
    getMulti(this);
    getSingle(this);
  },
  onHandleDelete : function(id,type,name){
    deleteVotes(this,type,id,name);
  },
  onHandleApprove : function(id,type,name){
    approve(this,type,id,name);
  },
  render: function() {
    if (this.state.isLoading) {
      return (
        <div>
          <img src='./Images/loading.gif'/>
        </div>
      );
    } else {
      console.log('RESULTS CONTAINER LOG: ', this);
      return(
        <ResultsListComponent
          single={this.state.districtSingleList}
          multi={this.state.districtMultiList}
          onHandleRefresh={this.onHandleRefresh}
          onHandleDelete={this.onHandleDelete}
          onHandleApprove={this.onHandleApprove}
          succesCreateText={this.state.succesCreateText}
          deletedResultsText={this.state.deletedResultsText}
        />
      );
    }
  }
});

window.ResultsListContainer = ResultsListContainer;
