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
function approve(self,type,id){
  axios
  .patch('/result/'+type+'/'+id+'/approve/')
  .then(function(response){
    getMulti(self);
    getSingle(self);
  })
  .catch(function(err){
    console.error('ResultsListContainer.onHandleApprove',err);
  });
}
function deleteVotes(self,type,id) {
  axios
  .delete('/result/'+type+'/'+id)
  .then(function(response){
    getMulti(self);
    getSingle(self);
  })
  .catch(function(err){
    console.error('ResultsListContainer.onHandleDelete',err);
  });
}

var ResultsListContainer = React.createClass({
  getInitialState: function() {
    return {
      isLoading : true,
      districtMultiList : [],
      districtSingleList : [],
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
  onHandleDelete : function(id,type){
    deleteVotes(this,type,id);
  },
  onHandleApprove : function(id,type){
    approve(this,type,id);
  },
  render: function() {
    if (this.state.isLoading) {
      return (
        <div>
          <img src='./Images/loading.gif'/>
        </div>
      );
    } else {
      return(
        <ResultsListComponent
          single={this.state.districtSingleList}
          multi={this.state.districtMultiList}
          onHandleRefresh={this.onHandleRefresh}
          onHandleDelete={this.onHandleDelete}
          onHandleApprove={this.onHandleApprove}
        />
      );
    }
  }
});

window.ResultsListContainer = ResultsListContainer;
